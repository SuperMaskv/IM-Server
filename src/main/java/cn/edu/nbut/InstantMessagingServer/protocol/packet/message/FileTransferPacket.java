package cn.edu.nbut.InstantMessagingServer.protocol.packet.message;


import cn.edu.nbut.InstantMessagingServer.protocol.packet.Packet;
import cn.edu.nbut.InstantMessagingServer.protocol.packet.PacketType;

/**
 * @author SuperMaskv
 * <p>
 * 文件传输沟通报文
 */

public class FileTransferPacket extends Packet {
    private String fileSender;
    private String fileReceiver;
    private boolean requestFlag;
    private String receiverAddress;
    private int receiverPort;
    private String fileName;
    private boolean agreeFlag;

    @Override
    public byte getType() {
        return PacketType.FILE_TRANSFER;
    }

    public String getFileSender() {
        return fileSender;
    }

    public void setFileSender(String fileSender) {
        this.fileSender = fileSender;
    }

    public String getFileReceiver() {
        return fileReceiver;
    }

    public void setFileReceiver(String fileReceiver) {
        this.fileReceiver = fileReceiver;
    }

    public boolean isRequestFlag() {
        return requestFlag;
    }

    public void setRequestFlag(boolean requestFlag) {
        this.requestFlag = requestFlag;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public int getReceiverPort() {
        return receiverPort;
    }

    public void setReceiverPort(int receiverPort) {
        this.receiverPort = receiverPort;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isAgreeFlag() {
        return agreeFlag;
    }

    public void setAgreeFlag(boolean agreeFlag) {
        this.agreeFlag = agreeFlag;
    }

    @Override
    public String toString() {
        return "FileTransferPacket{" +
                "fileSender='" + fileSender + '\'' +
                ", fileReceiver='" + fileReceiver + '\'' +
                ", requestFlag=" + requestFlag +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverPort=" + receiverPort +
                ", fileName='" + fileName + '\'' +
                ", agreeFlag=" + agreeFlag +
                '}';
    }
}
