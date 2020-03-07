package cn.edu.nbut.InstantMessagingServer.protocol.packet.group;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 为群组添加用户请求报文
 */

public class AddUserPacket extends Packet {

    private String userName;
    private int groupId;

    @Override
    public byte getType() {
        return PacketType.ADD_USER;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "AddUserPacket{" +
                "userName='" + userName + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
