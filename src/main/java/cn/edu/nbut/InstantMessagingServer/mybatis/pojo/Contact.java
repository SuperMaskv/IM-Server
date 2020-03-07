package cn.edu.nbut.InstantMessagingServer.mybatis.pojo;

import java.util.Arrays;

/**
 * @author SuperMaskv
 * <p>
 * 联系人pojo
 */
public class Contact {
	private String userName;
	private String contactName;
	private String alias;
	private byte[] photo;

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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Contact{" +
				"userName='" + userName + '\'' +
				", contactName='" + contactName + '\'' +
				", alias='" + alias + '\'' +
				", photo=" + Arrays.toString(photo) +
				'}';
	}
}
