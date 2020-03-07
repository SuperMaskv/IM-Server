package cn.edu.nbut.InstantMessagingServer.netty.handler.contact;

import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.ContactMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.EditContactAliasPacket;
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

    @Autowired
    private ContactMapper contactMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , EditContactAliasPacket editContactAliasPacket) throws Exception {
        if (!ConnectionMap.getInstance().isTokenExist(editContactAliasPacket.getToken())) return;


        Contact contact = new Contact();
        contact.setUserName(editContactAliasPacket.getUserName());
        contact.setContactName(editContactAliasPacket.getContactName());
        contact.setAlias(editContactAliasPacket.getAlias());

        if (contactMapper.isContactExist(editContactAliasPacket.getUserName(), editContactAliasPacket.getContactName()) == 1) {
            contactMapper.editContactAlias(contact);

            channelHandlerContext.channel().writeAndFlush(editContactAliasPacket);
        } else {
            //修改失败
        }
    }
}
