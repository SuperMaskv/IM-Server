package cn.edu.nbut.InstantMessagingServer.netty.handler.message;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.FileTransferPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.InetSocketAddress;

/**
 * @author SuperMaskv
 * <p>
 * 文件传输沟通报文处理器
 */
@Component
@ChannelHandler.Sharable
public class FileTransferPacketHandler extends SimpleChannelInboundHandler<FileTransferPacket> {
    @Autowired
    private ConnectionMap connectionMap;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , FileTransferPacket fileTransferPacket) throws Exception {
        if (!connectionMap.isTokenExist(fileTransferPacket.getToken())) return;
        if (!connectionMap.isUserExist(fileTransferPacket.getFileReceiver())) return;
        if (!connectionMap.isUserExist(fileTransferPacket.getFileSender())) return;

        var receiverChannel = connectionMap
                .getChannelByUserName(fileTransferPacket.getFileReceiver());

        if (fileTransferPacket.isRequestFlag()) {
            //请求报文:将请求报文转发给接收用户
            receiverChannel.writeAndFlush(fileTransferPacket);
        } else {
            //应答报文：根据agreeFlag来分别处理

            var senderChannel = connectionMap
                    .getChannelByUserName(fileTransferPacket.getFileSender());

            if (fileTransferPacket.isAgreeFlag()) {
                //如果接收方同意将接收文件
                try {
                    fileTransferPacket.setReceiverAddress(((InetSocketAddress) receiverChannel.remoteAddress()).getAddress().toString());
                    fileTransferPacket.setReceiverPort(((InetSocketAddress) receiverChannel.remoteAddress()).getPort());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            senderChannel.writeAndFlush(fileTransferPacket);
        }
    }
}
