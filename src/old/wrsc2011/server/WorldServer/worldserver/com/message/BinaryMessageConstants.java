package worldserver.com.message;

public interface BinaryMessageConstants extends SailBoatBinaryMessageConstants {

    // incomming, messages
    public static final byte MSG_ID_COM_CLOSED = (byte) 0xE0;
    public static final byte MSG_ID_COM_OPENED = (byte) 0xE1;
    // incomming, message length
    public static final byte MSG_LEN_COM_CLOSED = 1;
    public static final byte MSG_LEN_COM_OPENED = 2;

}
