package cn.edu.nbut.InstantMessagingServer.service;

import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import io.netty.channel.Channel;

import java.util.List;

public interface ContactService {
    /**
     * 根据用户名获取用户的联系人列表
     *
     * @param userName 用户名
     * @return 联系人列表
     */
    List<Contact> getContactListByUserName(String userName);

    /**
     * 根据联系人列表来获取在线联系人的 Channel 列表。
     *
     * @param contactList 联系人列表
     * @return 在线联系人的 Channel 列表
     */
    List<Channel> getLoggedContactChannel(List<Contact> contactList);

    /**
     * 根据用户名获取 Channel
     * @param userName 用户名
     * @return 对应用户的 Channel
     */
    Channel getLoggedContactChannel(String userName);

    /**
     * 根据联系人列表来获取在线联系人的用户名列表。
     *
     * @param contactList 联系人列表
     * @return 在线联系人的用户名列表
     */
    List<String> getLoggedContactName(List<Contact> contactList);

    /**
     * 为用户添加联系人
     *
     * @param contact 联系人对象
     */
    void addContact(Contact contact);

    /**
     * 根据用户名和联系人用户名查找联系人信息
     * @param userName 用户名
     * @param contactName 联系人用户名
     * @return Contact 对象
     */
    Contact getContact(String userName, String contactName);
}
