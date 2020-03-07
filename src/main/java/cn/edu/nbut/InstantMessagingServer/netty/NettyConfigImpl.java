package cn.edu.nbut.InstantMessagingServer.netty;


import cn.edu.nbut.InstantMessagingServer.netty.handler.common.PacketDecoder;
import cn.edu.nbut.InstantMessagingServer.netty.handler.common.PacketEncoder;
import cn.edu.nbut.InstantMessagingServer.netty.handler.common.Spliter;
import cn.edu.nbut.InstantMessagingServer.netty.handler.contact.AddContactPacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.contact.EditContactAliasPacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.contact.RemoveContactPacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.group.*;
import cn.edu.nbut.InstantMessagingServer.netty.handler.message.FileTransferPacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.message.ToGroupMessagePacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.message.ToUserMessagePacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.user.LoginPacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.user.LogoutPacketHandler;
import cn.edu.nbut.InstantMessagingServer.netty.handler.user.RegisterPacketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfigImpl implements NettyConfig {
    private static final Logger LOGGER = LogManager.getLogger(NettyConfigImpl.class);

    private final ServerBootstrap serverBootstrap;
    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    private Class<? extends ServerChannel> channelClass;

    @Autowired
    private LoginPacketHandler loginPacketHandler;
    @Autowired
    private RegisterPacketHandler registerPacketHandler;
    @Autowired
    private LogoutPacketHandler logoutPacketHandler;
    @Autowired
    private AddContactPacketHandler addContactPacketHandler;
    @Autowired
    private RemoveContactPacketHandler removeContactPacketHandler;
    @Autowired
    private EditContactAliasPacketHandler editContactAliasPacketHandler;
    @Autowired
    private CreateGroupPacketHandler createGroupPacketHandler;
    @Autowired
    private DismissGroupPacketHandler dismissGroupPacketHandler;
    @Autowired
    private EditGroupNamePacketHandler editGroupNamePacketHandler;
    @Autowired
    private AddUserPacketHandler addUserPacketHandler;
    @Autowired
    private RemoveUserPacketHandler removeUserPacketHandler;
    @Autowired
    private ToUserMessagePacketHandler toUserMessagePacketHandler;
    @Autowired
    private ToGroupMessagePacketHandler toGroupMessagePacketHandler;
    @Autowired
    private FileTransferPacketHandler fileTransferPacketHandler;

    @Override
    public void setChannel(Class<NioServerSocketChannel> nioServerSocketChannelClass) {

        this.channelClass = nioServerSocketChannelClass;
    }

    public NettyConfigImpl() {
        serverBootstrap = new ServerBootstrap();
    }

    public void setParentGroup() {
        this.parentGroup = new NioEventLoopGroup();
    }

    public void setParentGroup(int nThreads) {
        this.parentGroup = new NioEventLoopGroup(nThreads);
    }

    public void setChildGroup() {
        this.childGroup = new NioEventLoopGroup();
    }

    public void setChildGroup(int nThreads) {
        this.childGroup = new NioEventLoopGroup(nThreads);
    }

    public void setChannelClass(Class channelClass) {
        this.channelClass = channelClass;
    }

    public void setHandler() {
        //todo:setHandler
        if (validate()) {
            serverBootstrap
                    .group(parentGroup, childGroup)
                    .channel(channelClass)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //心跳检测处理器
//							socketChannel.pipeline().addLast("HeartBeatHandler", new HeartBeatHandler());
                            //拆包、MAGIC校验
                            socketChannel.pipeline().addLast("Spliter", new Spliter());
                            //反序列化
                            socketChannel.pipeline().addLast("PacketDecoder", new PacketDecoder());

                            //报文处理

                            //用户相关操作
                            //登录请求处理
                            socketChannel.pipeline().addLast("LoginPacketHandler", loginPacketHandler);
                            //注册请求处理
                            socketChannel.pipeline().addLast("RegisterPacketHandler", registerPacketHandler);
                            //登出请求处理
                            socketChannel.pipeline().addLast("LogoutPacketHandler", logoutPacketHandler);

                            //联系人相关操作
                            //添加联系人
                            socketChannel.pipeline().addLast("AddContactPacketHandler", addContactPacketHandler);
                            //删除联系人
                            socketChannel.pipeline().addLast("RemoveContactPacketHandler", removeContactPacketHandler);
                            //修改联系人备注
                            socketChannel.pipeline().addLast("EditContactAliasPacketHandler", editContactAliasPacketHandler);

                            //群组相关操作
                            //创建群组
                            socketChannel.pipeline().addLast("CreateGroupPacketHandler", createGroupPacketHandler);
                            //解散群组
                            socketChannel.pipeline().addLast("DismissGroupPacketHandler", dismissGroupPacketHandler);
                            //给群组改名
                            socketChannel.pipeline().addLast("EditGroupNamePacketHandler", editGroupNamePacketHandler);
                            //添加用户到群组
                            socketChannel.pipeline().addLast("AddUserPacketHandler", addUserPacketHandler);
                            //将用户移出群组
                            socketChannel.pipeline().addLast("RemoveUserPacketHandler", removeUserPacketHandler);

                            //消息相关操作
                            //私聊消息
                            socketChannel.pipeline().addLast("ToUserMessagePacketHandler", toUserMessagePacketHandler);
                            //群组消息
                            socketChannel.pipeline().addLast("ToGroupMessagePacketHandler", toGroupMessagePacketHandler);
                            //文件传输沟通
                            socketChannel.pipeline().addLast("FileTransferPacketHandler", fileTransferPacketHandler);

                            //序列化
                            socketChannel.pipeline().addLast("PacketEncoder", new PacketEncoder());
                        }
                    });
        }
    }

    public void bind(int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("服务器绑定到端口：" + port);
                } else {
                    LOGGER.info("服务器绑定到端口：" + port + "失败，尝试绑定到端口：" + (port + 1));
                    bind(port + 1);
                }
            }
        });
    }

    private boolean validate() {
        return this.parentGroup != null
                && this.childGroup != null
                && this.channelClass != null;
    }
}
