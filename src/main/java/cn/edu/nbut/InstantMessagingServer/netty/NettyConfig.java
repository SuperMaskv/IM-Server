package cn.edu.nbut.InstantMessagingServer.netty;

import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 配置接口
 */
public interface NettyConfig {
    void setParentGroup();

    void setParentGroup(int nThreads);

    void setChildGroup();

    void setChildGroup(int nThreads);

    void setChannelClass(Class channelClass);

    void setHandler();

    void bind(int port);

	void setChannel(Class<NioServerSocketChannel> nioServerSocketChannelClass);
}
