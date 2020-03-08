package cn.edu.nbut.InstantMessagingServer.netty.handler.user;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.ContactMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.OfflineContactPacket;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.user.LogoutPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 登出报文Handler，将用户从连接池中移除
 */
@Component
@ChannelHandler.Sharable
public class LogoutPacketHandler extends SimpleChannelInboundHandler<LogoutPacket> {
    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ConnectionMap connectionMap;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , LogoutPacket logoutPacket) throws Exception {


        if (connectionMap.isTokenExist(logoutPacket.getToken())) {

            connectionMap.removeConnection(logoutPacket.getUserName(), logoutPacket.getToken());

            //通知用户的联系人用户已下线
            var Contacts = contactMapper.getContactList(logoutPacket.getUserName());
            OfflineContactPacket offline = new OfflineContactPacket();
            offline.setUserName(logoutPacket.getUserName());
            for (var contact :
                    Contacts) {
                if (connectionMap.isUserExist(contact.getContactName())) {
                    connectionMap
                            .getChannelByUserName(contact.getContactName())
                            .writeAndFlush(offline);
                }
            }
        }
    }
}
