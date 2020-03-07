package cn.edu.nbut.InstantMessagingServer.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SuperMaskv
 * <p>
 * user_group表Mapper
 */
public interface UserGroupMapper {
    //添加用户到群组
    int addUser(@Param("userId") int userId, @Param("groupId") int groupId);

    //将用户移除群组
    int removeUser(@Param("userId") int userId, @Param("groupId") int groupId);

    //查询群组成员
    List<String> queryGroupMember(@Param("groupId") int groupId);
}
