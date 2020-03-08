package cn.edu.nbut.InstantMessagingServer.netty.handler.user;


import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.ContactListPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.OnlineContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.ToUserMessagePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.LoginPacket;
import cn.edu.nbut.InstantMessagingServer.service.ContactService;
import cn.edu.nbut.InstantMessagingServer.service.OfflineMessageService;
import cn.edu.nbut.InstantMessagingServer.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuperMaskv
 * <p>
 * 收到登录请求报文后，验证身份，下发token
 */
@Component
@ChannelHandler.Sharable
public class LoginPacketHandler extends SimpleChannelInboundHandler<LoginPacket> {
    private UserService userService;
    private ContactService contactService;
    private OfflineMessageService offlineMessageService;

    public LoginPacketHandler(UserService userService,
                              ContactService contactService,
                              OfflineMessageService offlineMessageService) {
        this.userService = userService;
        this.contactService = contactService;
        this.offlineMessageService = offlineMessageService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx
            , LoginPacket loginPacket) throws Exception {

        ResponsePacket responsePacket = new ResponsePacket();

        //如果登录成功，下发token
        if (userService.userAuth(loginPacket.getUserName(), loginPacket.getUserPwd())) {
            //判断用户是否在线,避免重复登录
            if (userService.isUserLogged(loginPacket.getUserName())) {
                responsePacket.setStatus(false);
                responsePacket.setPacketType(PacketType.LOGIN);
                responsePacket.setInfo("当前用户已在线，请勿重复登录");
                ctx.channel().writeAndFlush(responsePacket);
                return;
            }

            long token = userService.addUserToConnectionMap(loginPacket.getUserName(), ctx.channel());
            //构造并发送响应报文
            responsePacket.setStatus(true);
            responsePacket.setInfo(String.valueOf(token));
            responsePacket.setPacketType(PacketType.LOGIN);

            ctx.channel().writeAndFlush(responsePacket);

            //发送联系人列表
            List<Contact> contactList = contactService.getContactListByUserName(loginPacket.getUserName());

            ContactListPacket contactListPacket = new ContactListPacket();
            contactListPacket.setContacts(contactList);
            ctx.channel().writeAndFlush(contactListPacket);

            //查询联系人在线状态
            OnlineContactPacket onlineContactPacket = new OnlineContactPacket();
            onlineContactPacket.setOnlineContacts(contactService.getLoggedContactName(contactList));
            ctx.channel().writeAndFlush(onlineContactPacket);

            //向联系人发送上线信息
            OnlineContactPacket onlinePacket = new OnlineContactPacket();
            onlinePacket.setOnlineContacts(new ArrayList<>());
            onlinePacket.getOnlineContacts().add(loginPacket.getUserName());

            List<Channel> contactChannelList = contactService.getLoggedContactChannel(contactList);
            for (var channel :
                    contactChannelList) {
                if (channel != null) {
                    channel.writeAndFlush(onlinePacket);
                }
            }

            //拉取离线消息

            var offlineMessageList = offlineMessageService.getOfflineMessageByUserName(loginPacket.getUserName());
            for (var offlineMessage :
                    offlineMessageList) {
                ToUserMessagePacket packet = new ToUserMessagePacket();
                packet.setMsgSender(offlineMessage.getMsgSender());
                packet.setMsgRecipient(offlineMessage.getMsgRecipient());
                packet.setMsgContent(offlineMessage.getMsgContent());
                packet.setPhoto(offlineMessage.getPhoto());
                packet.setSendTime(offlineMessage.getSendTime());
                ctx.channel().writeAndFlush(packet);
            }

        } else {
            //如果登录失败，返回登录失败的响应报文
            responsePacket.setStatus(false);
            responsePacket.setInfo("用户名或密码错误");
            responsePacket.setPacketType(PacketType.LOGIN);

            ctx.channel().writeAndFlush(responsePacket);
        }

    }
}
