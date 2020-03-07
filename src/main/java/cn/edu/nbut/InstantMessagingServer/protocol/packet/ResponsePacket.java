package cn.edu.nbut.InstantMessagingServer.protocol.packet;


/**
 * @author SuperMaskv
 * <p>
 * 服务端应答报文，包含处理结果及相关信息
 */

public class ResponsePacket extends Packet {
    private byte packetType;
    private boolean status;
    private String info;


    @Override
    public byte getType() {
        return PacketType.SERVER_RESPONSE;
    }

    public byte getPacketType() {
        return packetType;
    }

    public void setPacketType(byte packetType) {
        this.packetType = packetType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ResponsePacket{" +
                "packetType=" + packetType +
                ", status=" + status +
                ", info='" + info + '\'' +
                '}';
    }
}
