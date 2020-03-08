package cn.edu.nbut.InstantMessagingServer.netty.handler.common;

import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SuperMaskv
 * <p>
 * 心跳检测处理器
 */
public class HeartBeatHandler extends IdleStateHandler {
	private static final Logger LOGGER = LogManager.getLogger(HeartBeatHandler.class);
	private static final int READER_IDLE_TIME = 30;

	@Autowired
	private ConnectionMap connectionMap;

	public HeartBeatHandler() {
		super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
		List<String> userNameList = connectionMap.getUserNameByChannel(ctx.channel());
		for (String userName : userNameList) {
//			connectionMap.removeConnection(userName);
		}

		ctx.channel().close();

	}
}
