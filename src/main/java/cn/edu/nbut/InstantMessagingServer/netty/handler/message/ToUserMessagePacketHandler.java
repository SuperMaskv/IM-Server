package cn.edu.nbut.InstantMessagingServer.netty.handler.message;


import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.ToUserOfflineMessage;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.ToUserMessagePacket;
import cn.edu.nbut.InstantMessagingServer.service.ContactService;
import cn.edu.nbut.InstantMessagingServer.service.OfflineMessageService;
import cn.edu.nbut.InstantMessagingServer.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 私聊数据包处理器
 * <p>
 * 检查发送方状态
 * 检查接受方在线状态，在线就将报文转发，不在线就存入离线消息数据库
 */
@Component
@ChannelHandler.Sharable
public class ToUserMessagePacketHandler extends SimpleChannelInboundHandler<ToUserMessagePacket> {
    private UserService userService;
    private OfflineMessageService offlineMessageService;
    private ContactService contactService;

    @Autowired
    public ToUserMessagePacketHandler(UserService userService, OfflineMessageService offlineMessageService, ContactService contactService) {
        this.userService = userService;
        this.offlineMessageService = offlineMessageService;
        this.contactService = contactService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , ToUserMessagePacket toUserMessagePacket) throws Exception {
        if (!userService.validateToken(toUserMessagePacket.getToken())) return;

        //判断接收方是否在线
        if (userService.isUserLogged(toUserMessagePacket.getMsgRecipient())) {

            contactService
                    .getLoggedContactChannel(toUserMessagePacket.getMsgRecipient())
                    .writeAndFlush(toUserMessagePacket);

        } else {

            ToUserOfflineMessage toUserOfflineMessage = new ToUserOfflineMessage();
            toUserOfflineMessage.setMsgSender(toUserMessagePacket.getMsgSender());
            toUserOfflineMessage.setMsgRecipient(toUserMessagePacket.getMsgRecipient());
            toUserOfflineMessage.setMsgContent(toUserMessagePacket.getMsgContent());
            toUserOfflineMessage.setPhoto(toUserMessagePacket.getPhoto());
            toUserOfflineMessage.setSendTime(toUserMessagePacket.getSendTime());

            offlineMessageService.addOfflineMessage(toUserOfflineMessage);
        }

    }
}
