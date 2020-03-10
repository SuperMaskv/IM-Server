package cn.edu.nbut.InstantMessagingServer.netty.handler.contact;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.RemoveContactPacket;
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
 * 删除联系人请求处理器
 * <p>
 * 判断联系人记录是否存在，存在就删除，不存在就拉倒，返回执行结果
 */
@Component
@ChannelHandler.Sharable
public class RemoveContactPacketHandler extends SimpleChannelInboundHandler<RemoveContactPacket> {
    private UserService userService;
    private ContactService contactService;

    @Autowired
    public RemoveContactPacketHandler(UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , RemoveContactPacket removeContactPacket) throws Exception {
        if (!userService.validateToken(removeContactPacket.getToken())) return;


        if (contactService.isContactExist(removeContactPacket.getUserName()
                , removeContactPacket.getContactName())) {
            contactService.removeContact(removeContactPacket.getUserName()
                    , removeContactPacket.getContactName());
            contactService.removeContact(removeContactPacket.getContactName()
                    , removeContactPacket.getUserName());

            channelHandlerContext.channel().writeAndFlush(removeContactPacket);


            RemoveContactPacket response = new RemoveContactPacket();
            response.setUserName(removeContactPacket.getContactName());
            response.setContactName(removeContactPacket.getUserName());
            if (userService.isUserLogged(removeContactPacket.getContactName())) {
                contactService
                        .getLoggedContactChannel(removeContactPacket.getContactName())
                        .writeAndFlush(response);
            }
        } else {
            //删除失败
        }
    }
}
