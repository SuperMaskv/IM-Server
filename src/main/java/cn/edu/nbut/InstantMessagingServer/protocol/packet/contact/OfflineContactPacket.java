package cn.edu.nbut.InstantMessagingServer.protocol.packet.contact;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 */

public class OfflineContactPacket extends Packet {
    private String userName;

    @Override
    public byte getType() {
        return PacketType.OFFLINE_CONTACT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "OfflineContactPacket{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
