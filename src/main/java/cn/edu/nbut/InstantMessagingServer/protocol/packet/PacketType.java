package cn.edu.nbut.InstantMessagingServer.protocol.packet;

/**
 * @author SuperMaskv
 * <p>
 * 包含所有报文类型的定义
 */
public interface PacketType {
	byte SERVER_RESPONSE = 0x00;

	//用户相关
	//用户注册
	byte REGISTER = 0X01;
	//用户登录
	byte LOGIN = 0x02;
	//用户注销
	byte LOGOUT = 0x03;

	//联系人相关
	//添加联系人
	byte ADD_CONTACT = 0x04;
	//删除联系人
	byte REMOVE_CONTACT = 0x05;
	//修改联系人备注
	byte EDIT_CONTACT_ALIAS = 0x06;
	//返回联系人列表
	byte CONTACT_LIST = 0x0E;
	//在线联系人报文
	byte ONLINE_CONTACT = 0x0F;
	//向联系人通知下线状态
	byte OFFLINE_CONTACT = 0x10;

	//群组相关
	//创建群组
	byte CREATE_GROUP = 0x07;
	//解散群组
	byte DISMISS_GROUP = 0x08;
	//修改群组名称
	byte EDIT_GROUP_NAME = 0x09;
	//添加用户到群组
	byte ADD_USER = 0x0A;
	//将用户移除群组
	byte REMOVE_USER = 0x0B;

	//消息相关
	//私聊数据包
	byte TO_USER_MESSAGE = 0x0C;
	//群聊消息数据包
	byte TO_GROUP_MESSAGE = 0x0D;
	//文件沟通报文
	byte FILE_TRANSFER = 0x11;
}
