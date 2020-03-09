package cn.edu.nbut.InstantMessagingServer.service.impl;

import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.service.UserService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private ConnectionMap connectionMap;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(ConnectionMap connectionMap, UserMapper userMapper) {
        this.connectionMap = connectionMap;
        this.userMapper = userMapper;
    }

    @Override
    public boolean isUserExist(String userName) {
        return userMapper.isUserExist(userName) == 1;
    }

    @Override
    public void registerUser(String userName, String userPwd) {
        userMapper.registerUser(userName, userPwd);
    }

    @Override
    public void removeUserFromConnectionMap(String userName, long token) {
        connectionMap.removeConnection(userName, token);
    }

    @Override
    public boolean validateToken(long token) {
        return connectionMap.isTokenExist(token);
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
