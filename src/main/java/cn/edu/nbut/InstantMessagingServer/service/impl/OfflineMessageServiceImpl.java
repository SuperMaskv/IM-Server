package cn.edu.nbut.InstantMessagingServer.service.impl;

import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.OfflineMessageMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.ToUserOfflineMessage;
import cn.edu.nbut.InstantMessagingServer.service.OfflineMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfflineMessageServiceImpl implements OfflineMessageService {
    private OfflineMessageMapper offlineMessageMapper;

    @Autowired
    public OfflineMessageServiceImpl(OfflineMessageMapper offlineMessageMapper) {
        this.offlineMessageMapper = offlineMessageMapper;
    }

    @Override
    public List<ToUserOfflineMessage> getOfflineMessageByUserName(String userName) {
        //获取用户离线消息
        List<ToUserOfflineMessage> offlineMessageList = offlineMessageMapper.getOfflineMessage(userName);
        //清空用户离线消息
        offlineMessageMapper.deleteUserOfflineMessage(userName);
        return offlineMessageList;
    }
}
