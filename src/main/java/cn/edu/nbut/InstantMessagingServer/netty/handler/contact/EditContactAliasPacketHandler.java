package cn.edu.nbut.InstantMessagingServer.netty.handler.contact;

import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.EditContactAliasPacket;
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
 * 修改联系人备注请求报文处理器
 * <p>
 * 判断是否有对应联系人信息，存在就修改，不存在就拉倒，并返回执行结果报文
 */
@Component
@ChannelHandler.Sharable
public class EditContactAliasPacketHandler extends SimpleChannelInboundHandler<EditContactAliasPacket> {

    private UserService userService;
    private ContactService contactService;

    @Autowired
    public EditContactAliasPacketHandler(UserService userService,
                                         ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , EditContactAliasPacket editContactAliasPacket) throws Exception {
        if (!userService.validateToken(editContactAliasPacket.getToken())) return;


        Contact contact = new Contact();
        contact.setUserName(editContactAliasPacket.getUserName());
        contact.setContactName(editContactAliasPacket.getContactName());
        contact.setAlias(editContactAliasPacket.getAlias());

        if (contactService.isContactExist(editContactAliasPacket.getUserName(),
                editContactAliasPacket.getContactName())) {
            contactService.editContactAlias(contact);

            channelHandlerContext.channel().writeAndFlush(editContactAliasPacket);
        } else {
            //修改失败
        }
    }
}
