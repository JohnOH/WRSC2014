package worldserver.com.message;

public interface SailBoatBinaryMessageConstants {

    // from boat, messages
    public static final byte MSG_ID_WIND = 0x05;    
    public static final byte MSG_ID_GPS_DATA = 0x09;
    // from boat, message length
    public static final byte MSG_LEN_WIND = 4;    
    public static final byte MSG_LEN_GPS_POS = 13;    
    // general
    public static final byte MSG_SEPERATOR = 0x52;
    public static final byte MSG_OVERHEAD = 2; // length and (SEPERATOR or checksum)
    // scaling factors for int to double
    public static final double GPS_FACTOR = 10000000.0d;
    public static final double WIND_SPEED_FACTOR = 1.0d;
    public static final double WIND_DIRECTION_FACTOR = 1.0d;

}
