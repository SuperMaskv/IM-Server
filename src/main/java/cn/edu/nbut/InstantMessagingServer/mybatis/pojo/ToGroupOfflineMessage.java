package cn.edu.nbut.InstantMessagingServer.mybatis.pojo;


/**
 * @author SuperMaskv
 * <p>
 * 群聊离线消息pojo
 */
public class ToGroupOfflineMessage {
    private String msgSender;
    private String msgRecipient;
    private int msgRecipientGroup;
    private String msgContent;

    public String getMsgSender() {
        return msgSender;
    }

    public void setMsgSender(String msgSender) {
        this.msgSender = msgSender;
    }

    public String getMsgRecipient() {
        return msgRecipient;
    }

    public void setMsgRecipient(String msgRecipient) {
        this.msgRecipient = msgRecipient;
    }

    public int getMsgRecipientGroup() {
        return msgRecipientGroup;
    }

    public void setMsgRecipientGroup(int msgRecipientGroup) {
        this.msgRecipientGroup = msgRecipientGroup;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "ToGroupOfflineMessage{" +
                "msgSender='" + msgSender + '\'' +
                ", msgRecipient='" + msgRecipient + '\'' +
                ", msgRecipientGroup=" + msgRecipientGroup +
                ", msgContent='" + msgContent + '\'' +
                '}';
    }
}
