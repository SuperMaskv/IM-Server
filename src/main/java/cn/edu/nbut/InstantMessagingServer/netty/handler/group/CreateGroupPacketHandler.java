package cn.edu.nbut.InstantMessagingServer.netty.handler.group;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.GroupMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.group.CreateGroupPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author SuperMaskv
 * <p>
 * 创建群组请求报文处理器
 * <p>
 * 先检查创建用户是否合法，再执行相关操作
 *
 * todo:通知群组内所有用户
 */
public class CreateGroupPacketHandler extends SimpleChannelInboundHandler<CreateGroupPacket> {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private GroupMapper groupMapper;

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext
			, CreateGroupPacket createGroupPacket) throws Exception {

		ResponsePacket responsePacket = new ResponsePacket();

		if (userMapper.isUserExist(createGroupPacket.getCreateUserName()) == 1) {
			groupMapper.createGroup(createGroupPacket.getGroupName(), createGroupPacket.getCreateUserName());


			responsePacket.setStatus(true);
		} else {

			responsePacket.setStatus(false);
			responsePacket.setInfo("群组创建失败，非法创建用户");
		}

		channelHandlerContext.channel().writeAndFlush(responsePacket);
	}
}
