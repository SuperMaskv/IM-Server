package cn.edu.nbut.InstantMessagingServer.protocol.packet.group;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 修改群组名称请求报文
 */

public class EditGroupNamePacket extends Packet {
    private int groupId;
    private String groupName;

    @Override
    public byte getType() {
        return PacketType.EDIT_GROUP_NAME;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "EditGroupNamePacket{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
