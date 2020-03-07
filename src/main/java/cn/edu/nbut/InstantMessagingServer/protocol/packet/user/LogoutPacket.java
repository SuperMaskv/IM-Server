package cn.edu.nbut.InstantMessagingServer.protocol.packet.user;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 登出报文
 */

public class LogoutPacket extends Packet {
    private String userName;

    @Override
    public byte getType() {
        return PacketType.LOGOUT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "LogoutPacket{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
