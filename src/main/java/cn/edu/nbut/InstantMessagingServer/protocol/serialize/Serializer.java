package cn.edu.nbut.InstantMessagingServer.protocol.serialize;

import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author SuperMaskv
 * <p>
 * Gson序列化工具类
 */
public class Serializer {
	public static byte[] serialize(Object object) {

		return new Gson().toJson(object).getBytes(StandardCharsets.UTF_8);
	}

	public static <T> T deserialize(byte[] bytes, Class<? extends Packet> clazz) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(byte[].class, new ByteArrayDeserializer());
		var test=new String(bytes, StandardCharsets.UTF_8);
		System.out.println(test);
		return gsonBuilder.create().fromJson(new String(bytes, StandardCharsets.UTF_8), (Type) clazz);
	}

	private static class ByteArrayDeserializer implements JsonDeserializer<byte[]> {
		@Override
		public byte[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			return jsonElement.getAsString().getBytes(StandardCharsets.UTF_8);
		}
	}
}
