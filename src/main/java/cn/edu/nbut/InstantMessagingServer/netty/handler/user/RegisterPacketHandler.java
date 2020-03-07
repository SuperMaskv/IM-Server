package cn.edu.nbut.InstantMessagingServer.netty.handler.user;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.RegisterPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author SuperMaskv
 * <p>
 * 注册请求报文Handler
 */
public class RegisterPacketHandler extends SimpleChannelInboundHandler<RegisterPacket> {
	@Autowired
	private UserMapper userMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , RegisterPacket registerPacket) throws Exception {

        ResponsePacket responsePacket = new ResponsePacket();
        responsePacket.setPacketType(PacketType.REGISTER);

        try {

            userMapper.registerUser(registerPacket.getUserName(), registerPacket.getUserPwd());

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
