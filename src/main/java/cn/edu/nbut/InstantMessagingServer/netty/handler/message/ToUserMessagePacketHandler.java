package cn.edu.nbut.InstantMessagingServer.netty.handler.message;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.OfflineMessageMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.ToUserOfflineMessage;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.ToUserMessagePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 私聊数据包处理器
 * <p>
 * 检查发送方状态
 * 检查接受方在线状态，在线就将报文转发，不在线就存入离线消息数据库
 */
@Component
@ChannelHandler.Sharable
public class ToUserMessagePacketHandler extends SimpleChannelInboundHandler<ToUserMessagePacket> {
    @Autowired
    private OfflineMessageMapper offlineMessageMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , ToUserMessagePacket toUserMessagePacket) throws Exception {
        if (!ConnectionMap.getInstance().isTokenExist(toUserMessagePacket.getToken())) return;

        ConnectionMap connectionMap = ConnectionMap.getInstance();


        //判断接收方是否在线
        if (connectionMap.isUserExist(toUserMessagePacket.getMsgRecipient())) {

            connectionMap.getChannelByUserName(toUserMessagePacket.getMsgRecipient())
                    .writeAndFlush(toUserMessagePacket);

        } else {

            ToUserOfflineMessage toUserOfflineMessage = new ToUserOfflineMessage();
            toUserOfflineMessage.setMsgSender(toUserMessagePacket.getMsgSender());
            toUserOfflineMessage.setMsgRecipient(toUserMessagePacket.getMsgRecipient());
            toUserOfflineMessage.setMsgContent(toUserMessagePacket.getMsgContent());
            toUserOfflineMessage.setPhoto(toUserMessagePacket.getPhoto());
            toUserOfflineMessage.setSendTime(toUserMessagePacket.getSendTime());

            offlineMessageMapper.insertToUserOfflineMessage(toUserOfflineMessage);
        }

    }
}
