package cn.edu.nbut.InstantMessagingServer.netty.handler.group;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.GroupMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.group.EditGroupNamePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 修改群组名称请求报文处理器
 * <p>
 * 先判断群组是否存在，存在就改名，不存在就拉倒
 *
 * todo:通知群组内所有用户
 */
@Component
@ChannelHandler.Sharable
public class EditGroupNamePacketHandler extends SimpleChannelInboundHandler<EditGroupNamePacket> {
	@Autowired
	private GroupMapper groupMapper;

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext
			, EditGroupNamePacket editGroupNamePacket) throws Exception {

		ResponsePacket responsePacket = new ResponsePacket();

		if (groupMapper.isGroupExist(editGroupNamePacket.getGroupId()) == 1) {
			groupMapper.editGroupName(editGroupNamePacket.getGroupId(), editGroupNamePacket.getGroupName());

			responsePacket.setStatus(true);
		} else {

			responsePacket.setStatus(false);
			responsePacket.setInfo("不存在指定群组");
		}

		channelHandlerContext.channel().writeAndFlush(responsePacket);
	}
}
