package cn.edu.nbut.InstantMessagingServer.mybatis.pojo;


import java.util.Arrays;

/**
 * @author SuperMaskv
 */
public class User {
	private int user_id;
	private String user_name;
	private String user_pwd;
	private String create_time;
	private byte[] photo;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "User{" +
				"user_id=" + user_id +
				", user_name='" + user_name + '\'' +
				", user_pwd='" + user_pwd + '\'' +
				", create_time='" + create_time + '\'' +
				", photo=" + Arrays.toString(photo) +
				'}';
	}
}
