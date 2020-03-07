package cn.edu.nbut.InstantMessagingServer.mybatis;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MybatisTest {
	@Autowired
	private UserMapper userMapper;

	@Test
	void authTest() {
		Assertions.assertEquals(1, userMapper.loginAuth("super", "123456"));
	}
}
