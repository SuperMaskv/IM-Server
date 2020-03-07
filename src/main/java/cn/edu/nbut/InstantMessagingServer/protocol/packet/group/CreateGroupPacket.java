package cn.edu.nbut.InstantMessagingServer.protocol.packet.group;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 创建群组请求报文
 */

public class CreateGroupPacket extends Packet {

    private String groupName;
    private String createUserName;

    @Override
    public byte getType() {
        return PacketType.CREATE_GROUP;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public String toString() {
        return "CreateGroupPacket{" +
                "groupName='" + groupName + '\'' +
                ", createUserName='" + createUserName + '\'' +
                '}';
    }
}
