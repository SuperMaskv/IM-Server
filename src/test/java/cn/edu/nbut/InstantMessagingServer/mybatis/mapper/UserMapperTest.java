package cn.edu.nbut.InstantMessagingServer.mybatis.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void authTest() {
        Assertions.assertEquals(1, userMapper.loginAuth("super", "123456"));
    }

    @Test
    void registerUserTest() {
//        Assertions.assertEquals(1,userMapper.registerUser("asdfads","adsfasdf"));
    }
}
