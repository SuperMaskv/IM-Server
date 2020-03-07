package cn.edu.nbut.InstantMessagingServer.protocol.packet.group;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 解散群组请求报文
 */

public class DismissGroupPacket extends Packet {
    private int groupId;

    @Override
    public byte getType() {
        return PacketType.DISMISS_GROUP;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "DismissGroupPacket{" +
                "groupId=" + groupId +
                '}';
    }
}
