package sampleboat.control;

/**
 * This is a pure constant definition interface, not declaring any methods,
 * but defining the constants used by <code>BoatCommand</code> and <code>BoatMessage</code>
 * for the communication with the real sailboats.
 * @author Mucho
 *
 */
public interface BoatComConstants {
	
	//BOOT MAC-ADRESSEN:
	public static final long MAC_BLACKMAGIC = 0x000BCE03F924L;
	public static final long MAC_BRANDENBURG = 0x000BCE049BE0L;
	public static final long MAC_HOLSTEIN = 0x000BCE03F974L;
	public static final long MAC_MECKLENBURG = 0x000BCE049BD6L;
	public static final long MAC_PIXDAUMEN = 0x000BCE03F94AL;
	public static final long MAC_WESTFALEN = 0x000BCE03F972L;

	//ZUM BOOT
	public static final byte MSG_ID_SET_SERVOS = 0x01;
	public static final byte MSG_ID_SET_RUDDER = 0x02;
	public static final byte MSG_ID_SET_OUTPUTS = 0x0F;
	public static final byte MSG_ID_SET_AI_MODE = (byte) 0xA0;
	public static final byte MSG_ID_SET_COURSE = (byte) 0xA1;
	public static final byte MSG_ID_SET_WAYPOINT = (byte) 0xA2;
	public static final byte MSG_ID_SEND_MSG_TABLE = (byte) 0xB0;
	public static final byte MSG_ID_READ_SENSOR_TABLE = (byte) 0xB1;
	public static final byte MSG_ID_PING = (byte) 0xFE;
	
	public static final byte MSG_LEN_SET_SERVOS = 7;
	public static final byte MSG_LEN_SET_RUDDER = 3;
	public static final byte MSG_LEN_SET_OUTPUTS = 2;
	public static final byte MSG_LEN_SET_AI_MODE = 2;
	public static final byte MSG_LEN_SET_COURSE = 3;
	public static final byte MSG_LEN_SET_WAYPOINT = 9;
	public static final byte MSG_LEN_SEND_MSG_TABLE = 5;
	public static final byte MSG_LEN_READ_SENSOR_TABLE = 3;
	public static final byte MSG_LEN_PING = 1;
	
	
	//ZUM GATE
	public static final byte MSG_ID_COM_CLOSE = (byte) 0xE0;
	public static final byte MSG_ID_COM_OPEN = (byte) 0xE1;
	public static final byte MSG_ID_CON_AVAILABLE_COMS = (byte) 0xEF;
	public static final byte MSG_ID_GATE_PING = (byte) 0xFC;
	
	public static final byte MSG_LEN_COM_CLOSE = 1;
	public static final byte MSG_LEN_COM_OPEN = 15;
	public static final byte MSG_LEN_CON_AVAILABLE_COMS_TX = 1;
	public static final byte MSG_LEN_GATE_PING = 1;
	
	//VOM GATE
	public static final byte MSG_ID_COM_CLOSED = (byte) 0xE0;
	public static final byte MSG_ID_COM_OPENED = (byte) 0xE1;
	
	public static final byte MSG_LEN_COM_CLOSED = 1;
	public static final byte MSG_LEN_COM_OPENED = 2;
	public static final byte MSG_LEN_CON_AVAILABLE_COMS_RX = 7;
	
	//VOM BOOT
	public static final byte MSG_ID_MAG_HEADING = 0x01;
	public static final byte MSG_ID_MAGNETOMETER = 0x02;
	public static final byte MSG_ID_ACCELEROMETER = 0x03;
	public static final byte MSG_ID_TURN_RATE = 0x04;
	public static final byte MSG_ID_WIND = 0x05;
	public static final byte MSG_ID_WATER_SPEED = 0x06;
	public static final byte MSG_ID_WATER_LEVEL = 0x07;
	public static final byte MSG_ID_BATTERY = 0x08;
	public static final byte MSG_ID_GPS_POS = 0x09;
	public static final byte MSG_ID_GPS_RAW = 0x0A;
	public static final byte MSG_ID_SERVO_POSITIONS = 0x0B;
	public static final byte MSG_ID_WAYPOINT_REACHED = (byte) 0xA0;
	//public static final byte MSG_ID_SIGNAL_STRENGTH = (byte) 0xE2;
	public static final byte MSG_ID_ERROR = (byte) 0xFF;
	
	public static final byte MSG_LEN_MAG_HEADING = 7;
	public static final byte MSG_LEN_MAGNETOMETER = 7;
	public static final byte MSG_LEN_ACCELEROMETER = 7;
	public static final byte MSG_LEN_TURN_RATE = 3;
	public static final byte MSG_LEN_WIND = 4;
	public static final byte MSG_LEN_WATER_SPEED = 2;
	public static final byte MSG_LEN_WATER_LEVEL = 2;
	public static final byte MSG_LEN_BATTERY = 3;
	public static final byte MSG_LEN_GPS_POS = 13;
	//public static final byte MSG_LEN_GPS_RAW = ?+1;
	public static final byte MSG_LEN_SERVO_POSITIONS = 7;
	public static final byte MSG_LEN_WAYPOINT_REACHED = 1;
	//public static final byte MSG_LEN_SIGNAL_STRENGTH = 2;
	public static final byte MSG_LEN_ERROR = 2;
	
	//ALLGEMEIN
	public static final byte MSG_SEPERATOR = 0x52;
	public static final byte MSG_OVERHEAD = 2; // length and (SEPERATOR or checksum)
	
	//GPS-DATEN SKALIERUNG
	public static final double GPS_SCALE = 10000000.0D;
}