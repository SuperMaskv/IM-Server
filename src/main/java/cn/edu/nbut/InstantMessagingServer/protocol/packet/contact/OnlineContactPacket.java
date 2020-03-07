package cn.edu.nbut.InstantMessagingServer.protocol.packet.contact;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

import java.util.List;

public class OnlineContactPacket extends Packet {
    private List<String> onlineContacts;

    @Override
    public byte getType() {
        return PacketType.ONLINE_CONTACT;
    }

    public List<String> getOnlineContacts() {
        return onlineContacts;
    }

    public void setOnlineContacts(List<String> onlineContacts) {
        this.onlineContacts = onlineContacts;
    }

    @Override
    public String toString() {
        return "OnlineContactPacket{" +
                "onlineContacts=" + onlineContacts +
                '}';
    }
}
