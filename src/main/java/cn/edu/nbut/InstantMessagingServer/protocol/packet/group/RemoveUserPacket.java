package cn.edu.nbut.InstantMessagingServer.protocol.packet.group;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 将用户移除群组请求数据包
 */

public class RemoveUserPacket extends Packet {
    private String userName;
    private int groupId;

    @Override
    public byte getType() {
        return PacketType.REMOVE_USER;
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
        return "RemoveUserPacket{" +
                "userName='" + userName + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
