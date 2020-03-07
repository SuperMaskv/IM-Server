package cn.edu.nbut.InstantMessagingServer.netty.handler.common;

import cn.edu.nbut.InstantMessagingServer.protocol.PacketCodec;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author SuperMaskv
 * <p>
 * Object to Bytes Handler
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext
			, Packet packet, ByteBuf byteBuf) throws Exception {
		PacketCodec.INSTANCE.encode(packet, byteBuf);
	}
}
