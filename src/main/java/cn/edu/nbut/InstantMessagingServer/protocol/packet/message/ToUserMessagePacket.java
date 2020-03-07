package cn.edu.nbut.InstantMessagingServer.protocol.packet.message;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 私聊消息数据包
 */

public class ToUserMessagePacket extends Packet {

    private String msgSender;

    private String msgRecipient;

    private String msgContent;

    private String photo;

    private String sendTime;


    @Override
    public byte getType() {
        return PacketType.TO_USER_MESSAGE;
    }

    public String getMsgSender() {
        return msgSender;
    }

    public void setMsgSender(String msgSender) {
        this.msgSender = msgSender;
    }

    public String getMsgRecipient() {
        return msgRecipient;
    }

    public void setMsgRecipient(String msgRecipient) {
        this.msgRecipient = msgRecipient;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "ToUserMessagePacket{" +
                "msgSender='" + msgSender + '\'' +
                ", msgRecipient='" + msgRecipient + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", photo='" + photo + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
