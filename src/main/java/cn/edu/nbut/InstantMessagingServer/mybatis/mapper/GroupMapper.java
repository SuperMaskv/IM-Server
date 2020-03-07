package cn.edu.nbut.InstantMessagingServer.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author SuperMaskv
 * <p>
 * 群组Mapper
 */
public interface GroupMapper {
    //创建群组
    int createGroup(@Param("groupName") String groupName, @Param("createUserName") String createUserName);

    //解散群组
    int dismissGroup(@Param("groupId") int groupId);

    //判断群组是否存在
    int isGroupExist(@Param("groupId") int groupId);

    //修改群组名称
    int editGroupName(@Param("groupId") int groupId, @Param("groupName") String groupName);
}
