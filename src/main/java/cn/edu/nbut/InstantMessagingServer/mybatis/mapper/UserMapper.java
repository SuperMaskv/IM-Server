package cn.edu.nbut.InstantMessagingServer.mybatis.mapper;

import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


public interface UserMapper {
	//获取所有用户列表
	List<User> getUserList();

	//查询用户是否存在
	int isUserExist(@Param("userName") String userName);

	//登录验证
	int loginAuth(@Param("userName") String userName, @Param("userPwd") String userPwd);

	//用户注册
	int registerUser(@Param("userName") String userName, @Param("userPwd") String userPwd);

	//查询用户ID
	int queryUserId(@Param("userName") String userName);
}
