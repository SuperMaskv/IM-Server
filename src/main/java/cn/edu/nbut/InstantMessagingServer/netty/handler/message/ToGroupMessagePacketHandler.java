package cn.edu.nbut.InstantMessagingServer.netty.handler.message;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.OfflineMessageMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserGroupMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.ToGroupOfflineMessage;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.ToGroupMessagePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author SuperMaskv
 * <p>
 * 群聊消息数据包处理器
 * <p>
 * 将群聊消息发送给群组内所有用户,不在线的用户将消息写入离线数据库
 */
@Component
@ChannelHandler.Sharable
public class ToGroupMessagePacketHandler extends SimpleChannelInboundHandler<ToGroupMessagePacket> {
    @Autowired
    private UserGroupMapper userGroupMapper;
    private OfflineMessageMapper offlineMessageMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , ToGroupMessagePacket toGroupMessagePacket) throws Exception {
        ConnectionMap connectionMap = ConnectionMap.getInstance();
        ResponsePacket responsePacket = new ResponsePacket();


        //检查发送方是否合法
        if (connectionMap.isTokenExist(toGroupMessagePacket.getToken())) {

            //获取群组用户列表，并按照其是否在线决定处理方式
            List<String> userNameList = userGroupMapper.queryGroupMember(toGroupMessagePacket.getMsgRecipientGroup());
            for (String userName : userNameList) {

                if (connectionMap.isUserExist(userName)) {

                    connectionMap.getChannelByUserName(userName).writeAndFlush(toGroupMessagePacket);

                } else {

                    ToGroupOfflineMessage toGroupOfflineMessage = new ToGroupOfflineMessage();
                    toGroupOfflineMessage.setMsgSender(toGroupMessagePacket.getMsgSender());
                    toGroupOfflineMessage.setMsgRecipient(userName);
                    toGroupOfflineMessage.setMsgRecipientGroup(toGroupMessagePacket.getMsgRecipientGroup());
                    toGroupOfflineMessage.setMsgContent(toGroupMessagePacket.getMsgContent());

                    offlineMessageMapper.insertToGroupOfflineMessage(toGroupOfflineMessage);
                }

            }
        } else {

            responsePacket.setStatus(false);
            responsePacket.setInfo("用户登录状态异常，请尝试重新登录");

        }

        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
