package cn.edu.nbut.InstantMessagingServer.protocol.packet;


/**
 * @author SuperMaskv
 */

public abstract class Packet {

    long token;
    final byte VERSION = 1;

    public abstract byte getType();

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public byte getVERSION() {
        return VERSION;
    }
}
