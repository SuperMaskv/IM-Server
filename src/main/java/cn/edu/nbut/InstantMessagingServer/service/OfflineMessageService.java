package cn.edu.nbut.InstantMessagingServer.service;

import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.ToUserOfflineMessage;

import java.util.List;

public interface OfflineMessageService {
    /**
     * 根据用户名获取数据库中的离线消息，获取后清除在数据库中的相关记录
     *
     * @param userName 用户名
     * @return 离线消息列表
     */
    List<ToUserOfflineMessage> getOfflineMessageByUserName(String userName);

    /**
     * 将发送给用户的离线消息写入数据库中
     *
     * @param toUserOfflineMessage 离线消息对象
     */
    void addOfflineMessage(ToUserOfflineMessage toUserOfflineMessage);
}
