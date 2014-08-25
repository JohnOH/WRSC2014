package mmcom;

import java.text.*;
import worldserver.object.*;

public class BoatMessage implements BoatComConstants {

	/**
	 * This Method parses a message given by a byte array and sets the values contained
	 * in the message on the given target SailBoat.
	 * @param receivedBytes byte array containing the received message-bytes, inclusive
	 * length and checksum of the message (in the first and last byte respectively)
	 * @param target the SailBoat the message was addressed to, parsed values are set
	 * using the corresponding setters of the SailBoat
	 * @throws ParseException if the message length defined by the first byte of the given array
	 * doesn't match the length of the array itself <b>or</b> if the checksum is wrong <b>or</b>
	 * if the message ID is unknown.
	 * @throws NullPointerException if one of the parameters is <code>null</code>
	 */
	public static void parseAndSetValues(byte[] receivedBytes, MicroMagic target)
			throws ParseException, NullPointerException {
		int length = receivedBytes[0] & 0xFF; // length = MSG_ID + payload
		if (receivedBytes.length != length + MSG_OVERHEAD) {
			throw new ParseException(
					"Length of given array does not match specified command length!",
					0);
		}
		if (receivedBytes[length+1] != MSG_SEPERATOR) {
			throw new ParseException(
					"Message checksum (control symbol) is wrong!", length);
		}
                switch (receivedBytes[1]) {
		case MSG_ID_MAG_HEADING: {
			short azimuth, pitch, roll;
			azimuth = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
			pitch = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
			roll = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
			target.setMagHeading(new int[] {azimuth, pitch, roll});
			break;
		}
		case MSG_ID_MAGNETOMETER: {
			short x, y, z;
			x = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
			y = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
			z = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
			target.setMagnetometer(new int[] {x, y, z});
			break;
		}
		case MSG_ID_ACCELEROMETER: {
			short x, y, z;
			x = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
			y = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
			z = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
			target.setAccelerometer(new int[] {x, y, z});
			break;
		}
		case MSG_ID_TURN_RATE: {
			short x, y, z;
			x = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
			y = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
			z = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
			target.setTurnRate(new int[] {x, y, z});
			break;
//                        short turnRate = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
//			target.setTurnRate(turnRate);
//			break;
		}
		case MSG_ID_WIND: {
			int direction, speed;
			direction = ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
			speed = receivedBytes[4] & 0xFF;
			target.setWind(direction, speed);
			break;
		}
		case MSG_ID_WATER_SPEED: {
			int speed = receivedBytes[2] & 0xFF;
			target.setWaterSpeed(speed);
			break;
		}
		case MSG_ID_WATER_LEVEL: {
			int level = receivedBytes[2] & 0xFF;
			target.setWaterLevel(level);
			break;
		}
		case MSG_ID_BATTERY: {
			int battery1 = receivedBytes[2] & 0xFF;
			int battery2 = receivedBytes[3] & 0xFF;
			target.setBatteries(battery1, battery2);
			break;
		}
		case MSG_ID_GPS_POS: {
			int latitude, trueHeading, groundSpeed, satCount;
			int longitude;
			latitude = (receivedBytes[5] & 0xFF) << 24 | (receivedBytes[4] & 0xFF) << 16
					| (receivedBytes[3] & 0xFF) << 8 | (receivedBytes[2] & 0xFF);
			longitude = (receivedBytes[9] & 0xFF) << 24 | (receivedBytes[8] & 0xFF) << 16
			| (receivedBytes[7] & 0xFF) << 8 | (receivedBytes[6] & 0xFF);
			trueHeading = (receivedBytes[11] & 0xFF) << 8 | (receivedBytes[10] & 0xFF);
			groundSpeed = receivedBytes[12] & 0xFF;
			satCount = receivedBytes[13] & 0xFF;
			target.setGPSValues(latitude, longitude, trueHeading, groundSpeed, satCount);
			//System.out.println("LAT: "+latitude+" LON: "+longitude+" Sats: "+satCount);
			break;
		}
		case MSG_ID_GPS_RAW: {
			byte[] rawData = new byte[length-1];
			System.arraycopy(receivedBytes, 2, rawData, 0, length-1);
			target.setGps_rawData(rawData);
			break;
		}
		case MSG_ID_SERVO_POSITIONS: {
			int rudder, mailsail, foresail;
			rudder = (short)((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
			mailsail = (short)((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
			foresail = (short)((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
			target.setServos(new int[] {rudder, mailsail, foresail});
			break;
		}
		case MSG_ID_COM_CLOSED:
			// handled elsewhere ;-)
			break;
                case MSG_ID_COM_OPENED:
                        //System.out.println("COM_OPEN(ED): " + length);
			if (length >= MSG_LEN_COM_OPEN) {
				String team = new String(receivedBytes, 2, 8);
				long mac = (receivedBytes[10] & 0xFFL) | (receivedBytes[11] & 0xFFL) << 8
						 | (receivedBytes[12] & 0xFFL) << 16 | (receivedBytes[13] & 0xFFL) << 24
						 | (receivedBytes[14] & 0xFFL) << 32 | (receivedBytes[15] & 0xFFL) << 40;
				target.setId(Long.toHexString(mac));
				target.setTeam(team);
                                //System.out.println("COM OPENED: " + mac + "  " + team);
                        }
			break;
//		case MSG_ID_SIGNAL_STRENGTH:
//			int strength = receivedBytes[2] & 0xFF;
//			target.setSignalStrength(strength);
//			break;
		case MSG_ID_PING:
			// not supported
			break;
		case MSG_ID_ERROR:
			// TODO
			break;
		default:
			//throw new ParseException("Unknown Message ID: "
				//	+ Integer.toHexString(receivedBytes[1]), 1);
		}
	}
}
