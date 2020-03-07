package cn.edu.nbut.InstantMessagingServer.protocol.packet.message;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 群聊消息数据包
 */

public class ToGroupMessagePacket extends Packet {
	private String msgSender;
	private int msgRecipientGroup;
	private String msgContent;

	@Override
	public byte getType() {
		return PacketType.TO_GROUP_MESSAGE;
	}

	public String getMsgSender() {
		return msgSender;
	}

	public void setMsgSender(String msgSender) {
		this.msgSender = msgSender;
	}

	public int getMsgRecipientGroup() {
		return msgRecipientGroup;
	}

	public void setMsgRecipientGroup(int msgRecipientGroup) {
		this.msgRecipientGroup = msgRecipientGroup;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Override
	public String toString() {
		return "ToGroupMessagePacket{" +
				"msgSender='" + msgSender + '\'' +
				", msgRecipientGroup=" + msgRecipientGroup +
				", msgContent='" + msgContent + '\'' +
				'}';
	}
}
