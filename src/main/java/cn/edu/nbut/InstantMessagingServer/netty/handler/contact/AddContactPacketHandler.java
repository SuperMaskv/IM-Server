package cn.edu.nbut.InstantMessagingServer.netty.handler.contact;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.ContactMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.AddContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.ContactListPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.OnlineContactPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author SuperMaskv
 * <p>
 * 处理添加联系人请求
 */
@Component
@ChannelHandler.Sharable
public class AddContactPacketHandler extends SimpleChannelInboundHandler<AddContactPacket> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private ConnectionMap connectionMap;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , AddContactPacket addContactPacket) throws Exception {
        //查询token是否合法
        if (!connectionMap.isTokenExist(addContactPacket.getToken())) return;


        //判断用户和联系人是否存在
        if (userMapper.isUserExist(addContactPacket.getUserName()) == 1
                && userMapper.isUserExist(addContactPacket.getContactName()) == 1) {
            //用户存在就将联系人记录写入数据库
            Contact contact = new Contact();
            contact.setUserName(addContactPacket.getUserName());
            contact.setContactName(addContactPacket.getContactName());
            contact.setAlias(addContactPacket.getAlias());
            contactMapper.addContact(contact);

            //将联系人改动通知给用户
            ContactListPacket newContact = new ContactListPacket();
            newContact.setContacts(new ArrayList<>());
            newContact.getContacts().add(contactMapper.getContact(addContactPacket.getUserName()
                    , addContactPacket.getContactName()));
            channelHandlerContext.channel().writeAndFlush(newContact);
            //将联系人在线状态发送给用户
            if (connectionMap.isUserExist(addContactPacket.getContactName())) {
                OnlineContactPacket online = new OnlineContactPacket();
                online.setOnlineContacts(new ArrayList<>());
                online.getOnlineContacts().add(addContactPacket.getContactName());
                channelHandlerContext.channel().writeAndFlush(online);
            }

            //为联系人也添加记录
            Contact reverseContact = new Contact();
            reverseContact.setUserName(addContactPacket.getContactName());
            reverseContact.setContactName(addContactPacket.getUserName());
            contactMapper.addContact(reverseContact);

            //如果联系人也在线，将改动通知给联系人
            if (connectionMap.isUserExist(addContactPacket.getContactName())) {
                ContactListPacket rNewContact = new ContactListPacket();
                rNewContact.setContacts(new ArrayList<>());
                rNewContact.getContacts().add(contactMapper.getContact(addContactPacket.getContactName()
                        , addContactPacket.getUserName()));
                connectionMap
                        .getChannelByUserName(addContactPacket.getContactName())
                        .writeAndFlush(rNewContact);

                //更改用户状态为在线
                OnlineContactPacket online = new OnlineContactPacket();
                online.setOnlineContacts(new ArrayList<>());
                online.getOnlineContacts().add(addContactPacket.getUserName());
                connectionMap
                        .getChannelByUserName(addContactPacket.getContactName())
                        .writeAndFlush(online);
            }
        }
    }
}
