package cn.edu.nbut.InstantMessagingServer.mybatis.mapper;

import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.ToGroupOfflineMessage;
import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.ToUserOfflineMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SuperMaskv
 * <p>
 * 离线消息库的相关操作（包括私聊和群组聊天）
 */
public interface OfflineMessageMapper {
    //将离线消息写入数据库
    int insertToUserOfflineMessage(ToUserOfflineMessage toUserOfflineMessage);

    //从数据库读取离线消息
    List<ToUserOfflineMessage> getOfflineMessage(@Param("userName") String userName);

    //清空指定用户的离线消息
    void deleteUserOfflineMessage(@Param("userName") String userName);

    //写入群聊离线消息
    int insertToGroupOfflineMessage(ToGroupOfflineMessage toGroupOfflineMessage);
}
