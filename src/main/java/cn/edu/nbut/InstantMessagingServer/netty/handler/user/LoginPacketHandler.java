package cn.edu.nbut.InstantMessagingServer.netty.handler.user;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.ContactMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.OfflineMessageMapper;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.UserMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.ResponsePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.ContactListPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.OnlineContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.message.ToUserMessagePacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.LoginPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author SuperMaskv
 * <p>
 * 收到登录请求报文后，验证身份，下发token
 */
public class LoginPacketHandler extends SimpleChannelInboundHandler<LoginPacket> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private OfflineMessageMapper offlineMessageMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , LoginPacket loginPacket) throws Exception {

        ResponsePacket responsePacket = new ResponsePacket();

        int res = userMapper.loginAuth(loginPacket.getUserName(), loginPacket.getUserPwd());
        //如果登录成功，下发token
        if (res == 1) {
            //判断用户是否在线,避免重复登录
            if (ConnectionMap.getInstance().isUserExist(loginPacket.getUserName())) {
                responsePacket.setStatus(false);
                responsePacket.setPacketType(PacketType.LOGIN);
                responsePacket.setInfo("当前用户已在线，请勿重复登录");
                channelHandlerContext.channel().writeAndFlush(responsePacket);
                return;
            }

            long token = ConnectionMap.getInstance().addConnection(loginPacket.getUserName(), channelHandlerContext.channel());
            //构造并发送响应报文
            responsePacket.setStatus(true);
            responsePacket.setInfo(String.valueOf(token));
            responsePacket.setPacketType(PacketType.LOGIN);

            channelHandlerContext.channel().writeAndFlush(responsePacket);

            //发送联系人列表
            ContactListPacket contactListPacket = new ContactListPacket();
            contactListPacket.setContacts(contactMapper.getContactList(loginPacket.getUserName()));
            channelHandlerContext.channel().writeAndFlush(contactListPacket);

            //查询联系人在线状态
            OnlineContactPacket onlineContactPacket = new OnlineContactPacket();
            onlineContactPacket.setOnlineContacts(new ArrayList<>());
            for (var contact :
                    contactListPacket.getContacts()) {
                if (ConnectionMap.getInstance().isUserExist(contact.getContactName())) {
                    onlineContactPacket.getOnlineContacts().add(contact.getContactName());
                }
            }
            channelHandlerContext.channel().writeAndFlush(onlineContactPacket);

            //向联系人发送上线信息
            OnlineContactPacket onlinePacket = new OnlineContactPacket();
            onlinePacket.setOnlineContacts(new ArrayList<>());
            onlinePacket.getOnlineContacts().add(loginPacket.getUserName());
            for (var contact :
                    contactListPacket.getContacts()) {
                var channel = ConnectionMap.getInstance().getChannelByUserName(contact.getContactName());
                if (channel != null) {
                    channel.writeAndFlush(onlinePacket);
                }
            }

            //拉取离线消息

            var offlineMessageList = offlineMessageMapper.getOfflineMessage(loginPacket.getUserName());
            for (var offlineMessage :
                    offlineMessageList) {
                ToUserMessagePacket packet = new ToUserMessagePacket();
                packet.setMsgSender(offlineMessage.getMsgSender());
                packet.setMsgRecipient(offlineMessage.getMsgRecipient());
                packet.setMsgContent(offlineMessage.getMsgContent());
                packet.setPhoto(offlineMessage.getPhoto());
                packet.setSendTime(offlineMessage.getSendTime());
                channelHandlerContext.channel().writeAndFlush(packet);
            }

            //清空离线消息
            offlineMessageMapper.deleteUserOfflineMessage(loginPacket.getUserName());

        } else {
            //如果登录失败，返回登录失败的响应报文
            responsePacket.setStatus(false);
            responsePacket.setInfo("用户名或密码错误");
            responsePacket.setPacketType(PacketType.LOGIN);

            channelHandlerContext.channel().writeAndFlush(responsePacket);
        }

    }
}
