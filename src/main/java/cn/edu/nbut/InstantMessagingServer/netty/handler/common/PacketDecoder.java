package cn.edu.nbut.InstantMessagingServer.netty.handler.common;

import cn.edu.nbut.InstantMessagingServer.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		list.add(PacketCodec.INSTANCE.decode(byteBuf));
	}
}
