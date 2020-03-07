package cn.edu.nbut.InstantMessagingServer.netty.handler.message;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.FileTransferPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , FileTransferPacket fileTransferPacket) throws Exception {
        if (!ConnectionMap.getInstance().isTokenExist(fileTransferPacket.getToken())) return;
        if (!ConnectionMap.getInstance().isUserExist(fileTransferPacket.getFileReceiver())) return;
        if (!ConnectionMap.getInstance().isUserExist(fileTransferPacket.getFileSender())) return;

        var receiverChannel = ConnectionMap.getInstance()
                .getChannelByUserName(fileTransferPacket.getFileReceiver());

        if (fileTransferPacket.isRequestFlag()) {
            //请求报文:将请求报文转发给接收用户
            receiverChannel.writeAndFlush(fileTransferPacket);
        } else {
            //应答报文：根据agreeFlag来分别处理

            var senderChannel = ConnectionMap.getInstance()
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
