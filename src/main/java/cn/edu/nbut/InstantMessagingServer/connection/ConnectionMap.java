package cn.edu.nbut.InstantMessagingServer.connection;

import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author SuperMaskv
 * <p>
 * 管理已建立的连接
 */
@Component
@Scope("singleton")
public class ConnectionMap {
	private static final Logger LOGGER = LogManager.getLogger(ConnectionMap.class);

	private Map<String, Channel> channelMap = new HashMap<>();
	private Map<String, Long> tokenMap = new HashMap<>();
	private Set<Long> tokenSet = new HashSet<>();
	private final SecureRandom SECURE_RANDOM = new SecureRandom();

	public synchronized boolean isTokenExist(Long token) {
		return tokenSet.contains(token);
	}

	public synchronized long generateToken() {
		long token = Math.abs(SECURE_RANDOM.nextLong());

		while (isTokenExist(token)) {
			token = Math.abs(SECURE_RANDOM.nextLong());
		}

		return token;
	}


	public synchronized long addConnection(String userName, Channel channel) {
		long token = generateToken();
		tokenSet.add(token);
		tokenMap.put(userName, token);
		channelMap.put(userName, channel);
		LOGGER.info(userName + "已建立连接");
		return token;
	}

	public synchronized long removeConnection(String userName,long token) {
		tokenSet.remove(token);
		tokenMap.remove(userName);
		channelMap.remove(userName);
		LOGGER.warn(userName + "连接断开");
		return token;
	}

	public synchronized Channel getChannelByUserName(String userName) {
		return channelMap.get(userName);
	}

	public synchronized List<String> getUserNameByChannel(Channel channel) {
		Set<Map.Entry<String, Channel>> set = channelMap.entrySet();
		Iterator<Map.Entry<String, Channel>> iterator = set.iterator();
		List<String> res = new ArrayList<>();
		while (iterator.hasNext()) {
			Map.Entry<String, Channel> entry = iterator.next();
			if (entry.getValue().equals(channel)) {
				res.add(entry.getKey());
			}
		}
		return res;
	}

	public synchronized boolean isUserExist(String userName) {
		return channelMap.containsKey(userName);
	}
}
