package cn.edu.nbut.InstantMessagingServer.netty.handler.group;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.GroupMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserGroupMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.group.RemoveUserPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SuperMaskv
 * <p>
 * 将用户从群组移除数据包处理器
 * <p>
 * 判断是否存在对应的记录，再做处理
 * <p>
 * todo:通知群组内其他用户
 */
public class RemoveUserPacketHandler extends SimpleChannelInboundHandler<RemoveUserPacket> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , RemoveUserPacket removeUserPacket) throws Exception {

        ResponsePacket responsePacket = new ResponsePacket();

        if (userMapper.isUserExist(removeUserPacket.getUserName()) == 1
                && groupMapper.isGroupExist(removeUserPacket.getGroupId()) == 1) {
            userGroupMapper.removeUser(userMapper.queryUserId(removeUserPacket.getUserName())
                    , removeUserPacket.getGroupId());

            responsePacket.setStatus(true);
        } else {
            responsePacket.setStatus(false);
            responsePacket.setInfo("指定用户或群组不存在");
        }

        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
