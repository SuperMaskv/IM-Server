package cn.edu.nbut.InstantMessagingServer.netty.handler.group;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.GroupMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.group.DismissGroupPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 解散群组请求报文处理器
 * <p>
 * 判断指定群组是否存在，存在就删除，不存在就拉倒
 *
 * todo:通知群组内所有用户
 */
@Component
@ChannelHandler.Sharable
public class DismissGroupPacketHandler extends SimpleChannelInboundHandler<DismissGroupPacket> {
	@Autowired
	private GroupMapper groupMapper;

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext
			, DismissGroupPacket dismissGroupPacket) throws Exception {

		ResponsePacket responsePacket = new ResponsePacket();

		if (groupMapper.isGroupExist(dismissGroupPacket.getGroupId()) == 1) {
			groupMapper.dismissGroup(dismissGroupPacket.getGroupId());

			responsePacket.setStatus(true);
		} else {
			responsePacket.setStatus(false);
			responsePacket.setInfo("不存在指定群组");
		}

		channelHandlerContext.channel().writeAndFlush(responsePacket);
	}
}
