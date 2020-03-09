package cn.edu.nbut.InstantMessagingServer.netty.handler.user;

import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.RegisterPacket;
import cn.edu.nbut.InstantMessagingServer.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 注册请求报文Handler
 */
@Component
@ChannelHandler.Sharable
public class RegisterPacketHandler extends SimpleChannelInboundHandler<RegisterPacket> {
    private UserService userService;

    @Autowired
    public RegisterPacketHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , RegisterPacket registerPacket) throws Exception {

        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setPacketType(PacketType.REGISTER);

        try {

            userService.registerUser(registerPacket.getUserName(), registerPacket.getUserPwd());

            //用户注册成功，构建应答报文
            responsePacket.setStatus(true);
            responsePacket.setInfo("注册成功");
        } catch (Exception e) {
            //用户注册失败
            responsePacket.setStatus(false);
            responsePacket.setInfo("已存在相同用户名");
        } finally {
            channelHandlerContext.channel().writeAndFlush(responsePacket);
        }
    }
}
