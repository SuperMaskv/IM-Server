package cn.edu.nbut.InstantMessagingServer.netty.handler.contact;


import cn.edu.nbut.InstantMessagingServer.connection.ConnectionMap;
import cn.edu.nbut.InstantMessagingServer.mybatis.mapper.ContactMapper;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.contact.RemoveContactPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author SuperMaskv
 * <p>
 * 删除联系人请求处理器
 * <p>
 * 判断联系人记录是否存在，存在就删除，不存在就拉倒，返回执行结果
 */
@Component
@ChannelHandler.Sharable
public class RemoveContactPacketHandler extends SimpleChannelInboundHandler<RemoveContactPacket> {
    @Autowired
    private ContactMapper contactMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext
            , RemoveContactPacket removeContactPacket) throws Exception {
        if (!ConnectionMap.getInstance().isTokenExist(removeContactPacket.getToken())) return;


        if (contactMapper.isContactExist(removeContactPacket.getUserName()
                , removeContactPacket.getContactName()) == 1) {
            contactMapper.removeContact(removeContactPacket.getUserName()
                    , removeContactPacket.getContactName());
            contactMapper.removeContact(removeContactPacket.getContactName()
                    , removeContactPacket.getUserName());



            channelHandlerContext.channel().writeAndFlush(removeContactPacket);
            RemoveContactPacket response = new RemoveContactPacket();
            response.setUserName(removeContactPacket.getContactName());
            response.setContactName(removeContactPacket.getUserName());
            ConnectionMap.getInstance()
                    .getChannelByUserName(removeContactPacket.getContactName())
                    .writeAndFlush(response);
        } else {
        	//删除失败
        }
    }
}
