package cn.edu.nbut.InstantMessagingServer.netty.handler.group;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.GroupMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserGroupMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.group.AddUserPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SuperMaskv
 * <p>
 * 添加用户到群组请求报文处理器
 * <p>
 * 判断群组和用户是否存在，再查询用户ID，再写入数据库
 * <p>
 * todo:通知群组内所有用户
 */
public class AddUserPacketHandler extends SimpleChannelInboundHandler<AddUserPacket> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , AddUserPacket addUserPacket) throws Exception {

        ResponsePacket responsePacket = new ResponsePacket();

        if (userMapper.isUserExist(addUserPacket.getUserName()) == 1
                && groupMapper.isGroupExist(addUserPacket.getGroupId()) == 1) {
            userGroupMapper.addUser(userMapper.queryUserId(addUserPacket.getUserName())
                    , addUserPacket.getGroupId());


            responsePacket.setStatus(true);
        } else {

            responsePacket.setStatus(false);
            responsePacket.setInfo("指定用户或群组不存在");
        }

        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
