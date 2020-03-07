package cn.edu.nbut.InstantMessagingServer.protocol;

import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.AddContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.EditContactAliasPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.OfflineContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.RemoveContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.FileTransferPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.ToUserMessagePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.LoginPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.LogoutPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.RegisterPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SuperMaskv
 * <p>
 * 负责对数据进行编码解码
 * <p>
 * MAGIC short
 * version byte
 * type byte
 * length int
 * body(包含token)
 */
public class PacketCodec {
	public static final short MAGIC = (short) 0xabcd;
	public static final PacketCodec INSTANCE = new PacketCodec();
	private Map<Byte, Class<? extends Packet>> packetMap;

	public PacketCodec() {
		packetMap = new HashMap<>();
		packetMap.put(PacketType.LOGIN, LoginPacket.class);
		packetMap.put(PacketType.LOGOUT, LogoutPacket.class);
		packetMap.put(PacketType.REGISTER, RegisterPacket.class);
		packetMap.put(PacketType.ADD_CONTACT, AddContactPacket.class);
		packetMap.put(PacketType.OFFLINE_CONTACT, OfflineContactPacket.class);
		packetMap.put(PacketType.EDIT_CONTACT_ALIAS, EditContactAliasPacket.class);
		packetMap.put(PacketType.REMOVE_CONTACT, RemoveContactPacket.class);
		packetMap.put(PacketType.TO_USER_MESSAGE, ToUserMessagePacket.class);
		packetMap.put(PacketType.FILE_TRANSFER, FileTransferPacket.class);
	}

	public void encode(Packet packet, ByteBuf byteBuf) {
		byte[] bytes = Serializer.serialize(packet);

		//写入MAGIC
		byteBuf.writeShort(MAGIC);
		//写入版本标志
		byteBuf.writeByte(packet.getVERSION());
		//写入数据包类型
		byteBuf.writeByte(packet.getType());
		//写入数据长度
		byteBuf.writeInt(bytes.length);
		//写入序列化后的数据
		byteBuf.writeBytes(bytes);
	}

	public Packet decode(ByteBuf byteBuf) {
		//跳过MAGIC
		byteBuf.skipBytes(2);
		//跳过version
		byteBuf.skipBytes(1);

		byte type = byteBuf.readByte();
		int bytesLength = byteBuf.readInt();

		byte[] bytes = new byte[bytesLength];
		byteBuf.readBytes(bytes);

		return Serializer.deserialize(bytes, matchPacket(type));
	}

	public Class<? extends Packet> matchPacket(byte type) {
		return packetMap.get(type);
	}
}
