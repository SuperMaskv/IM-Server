package cn.edu.nbut.InstantMessagingServer.protocol.packet.user;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 用户注册数据包
 */

public class RegisterPacket extends Packet {

    private String userName;
    private String userPwd;

    @Override
    public byte getType() {
        return PacketType.REGISTER;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public String toString() {
        return "RegisterPacket{" +
                "userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }
}
