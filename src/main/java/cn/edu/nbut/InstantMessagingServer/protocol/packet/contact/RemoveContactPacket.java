package cn.edu.nbut.InstantMessagingServer.protocol.packet.contact;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 删除联系人请求报文
 */

public class RemoveContactPacket extends Packet {

    private String userName;
    private String contactName;

    @Override
    public byte getType() {
        return PacketType.REMOVE_CONTACT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public String toString() {
        return "RemoveContactPacket{" +
                "userName='" + userName + '\'' +
                ", contactName='" + contactName + '\'' +
                '}';
    }
}
