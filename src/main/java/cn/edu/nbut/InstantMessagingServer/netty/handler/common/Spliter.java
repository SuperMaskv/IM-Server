package cn.edu.nbut.InstantMessagingServer.netty.handler.common;

import cn.edu.nbut.InstantMessagingServer.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author SuperMaskv
 *
 * 拆包Handler，并拒绝非本协议数据包
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
	private static final Logger LOGGER = LogManager.getLogger(Spliter.class);
	public Spliter() {
		super(Integer.MAX_VALUE, 4, 4);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		//屏蔽非本协议数据包
		if (in.getShort(in.readerIndex()) != PacketCodec.MAGIC) {
			LOGGER.info("屏蔽非本协议数据包");
			ctx.channel().close();
			return null;
		}

		return super.decode(ctx, in);
	}
}
