package cn.edu.nbut.InstantMessagingServer.protocol.packet.contact;


import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

import java.util.List;

public class ContactListPacket extends Packet {
    private List<Contact> contacts;

    @Override
    public byte getType() {
        return PacketType.CONTACT_LIST;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "ContactListPacket{" +
                "contacts=" + contacts +
                '}';
    }
}
