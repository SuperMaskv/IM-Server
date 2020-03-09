package cn.edu.nbut.InstantMessagingServer.netty.handler.user;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.OfflineContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.LogoutPacket;
import cn.edu.nbut.InstantMessagingServer.service.ContactService;
import cn.edu.nbut.InstantMessagingServer.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 登出报文Handler，将用户从连接池中移除
 */
@Component
@ChannelHandler.Sharable
public class LogoutPacketHandler extends SimpleChannelInboundHandler<LogoutPacket> {
    private UserService userService;
    private ContactService contactService;

    @Autowired
    public LogoutPacketHandler(UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , LogoutPacket logoutPacket) throws Exception {
        if (userService.validateToken(logoutPacket.getToken())) {

            userService.removeUserFromConnectionMap(logoutPacket.getUserName(), logoutPacket.getToken());

            //通知用户的联系人用户已下线
            var Contacts = contactService.getContactListByUserName(logoutPacket.getUserName());
            var OnlineContactChannels = contactService.getLoggedContactChannel(Contacts);
            OfflineContactPacket offline = new OfflineContactPacket();
            offline.setUserName(logoutPacket.getUserName());
            for (var channel :
                    OnlineContactChannels) {
                channel.writeAndFlush(offline);
            }
        }
    }
}
