package cn.edu.nbut.InstantMessagingServer.service.impl;

import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.ContactMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import cn.edu.nbut.InstantMessagingServer.service.ContactService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private ConnectionMap connectionMap;
    private ContactMapper contactMapper;

    @Autowired
    public ContactServiceImpl(ConnectionMap connectionMap, ContactMapper contactMapper) {
        this.connectionMap = connectionMap;
        this.contactMapper = contactMapper;
    }

    @Override
    public void removeContact(String userName, String contactName) {
        contactMapper.removeContact(userName, contactName);
    }

    @Override
    public boolean editContactAlias(Contact contact) {
        return contactMapper.editContactAlias(contact) == 1;
    }

    @Override
    public boolean isContactExist(String userName, String contactName) {
        return contactMapper.isContactExist(userName, contactName) == 1;
    }

    @Override
    public Contact getContact(String userName, String contactName) {
        return contactMapper.getContact(userName, contactName);
    }

    @Override
    public Channel getLoggedContactChannel(String userName) {
        return connectionMap.getChannelByUserName(userName);
    }

    @Override
    public List<Channel> getLoggedContactChannel(List<Contact> contactList) {
        List<Channel> channelList = new ArrayList<>();
        for (var contact :
                contactList) {
            var contactName = contact.getContactName();
            if (connectionMap.isUserExist(contactName)) {
                channelList.add(connectionMap.getChannelByUserName(contactName));
            }
        }
        return channelList;
    }

    @Override
    public void addContact(Contact contact) {
        contactMapper.addContact(contact);
    }

    @Override
    public List<Contact> getContactListByUserName(String userName) {
        return contactMapper.getContactList(userName);
    }

    @Override
    public List<String> getLoggedContactName(List<Contact> contactList) {
        List<String> contactNameList = new ArrayList<>();
        for (var contact :
                contactList) {
            var contactName = contact.getContactName();
            if (connectionMap.isUserExist(contactName)) {
                contactNameList.add(contactName);
            }
        }
        return contactNameList;
    }
}
