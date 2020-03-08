package cn.edu.nbut.InstantMessagingServer.service.impl;

import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.service.UserService;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private ConnectionMap connectionMap;
    private UserMapper userMapper;

    public UserServiceImpl(ConnectionMap connectionMap, UserMapper userMapper) {
        this.connectionMap = connectionMap;
        this.userMapper = userMapper;
    }

    @Override
    public boolean userAuth(String userName, String userPwd) {
        return userMapper.loginAuth(userName, userPwd) == 1;
    }

    @Override
    public boolean isUserLogged(String userName) {
        return connectionMap.isUserExist(userName);
    }

    @Override
    public long addUserToConnectionMap(String userName, Channel channel) {
        return connectionMap.addConnection(userName, channel);
    }
}
