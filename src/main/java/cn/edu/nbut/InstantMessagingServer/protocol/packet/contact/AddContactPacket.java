package cn.edu.nbut.InstantMessagingServer.protocol.packet.contact;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 添加联系人报文
 */

public class AddContactPacket extends Packet {
	private String userName;
	private String contactName;
	private String alias;


	@Override
	public byte getType() {
		return PacketType.ADD_CONTACT;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "AddContactPacket{" +
				"userName='" + userName + '\'' +
				", contactName='" + contactName + '\'' +
				", alias='" + alias + '\'' +
				'}';
	}
}
