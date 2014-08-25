package worldserver.com.message;

import java.text.*;
import worldserver.object.*;

public class MicroMagicBinaryMessage implements BinaryMessageConstants, MicroMagicBinaryMessageConstants {

    public static void parseAndSetValues(byte[] receivedBytes, MicroMagic microMagic)
            throws ParseException, NullPointerException {
        SailBoatBinaryMessage.parseAndSetValues(receivedBytes, microMagic);
        int length = receivedBytes[0] & 0xFF; // length = MSG_ID + payload
        if (receivedBytes.length != length + MSG_OVERHEAD)
            throw new ParseException("Length of given array does not match specified command length!", 0);
        if (receivedBytes[length + 1] != MSG_SEPERATOR)
            throw new ParseException("Message checksum (control symbol) is wrong!", length);
        switch (receivedBytes[1]) {
            case MSG_ID_MAG_HEADING: {
                short azimuth, pitch, roll;
                azimuth = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
                pitch = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
                roll = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
                microMagic.setMagHeading(new int[]{azimuth, pitch, roll});
                break;
            }
            case MSG_ID_MAGNETOMETER: {
                short x, y, z;
                x = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
                y = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
                z = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
                microMagic.setMagnetometer(new int[]{x, y, z});
                break;
            }
            case MSG_ID_ACCELEROMETER: {
                short x, y, z;
                x = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
                y = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
                z = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
                microMagic.setAccelerometer(new int[]{x, y, z});
                break;
            }
            case MSG_ID_TURN_RATE: {
                short x, y, z;
                x = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
                y = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
                z = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
                microMagic.setTurnRate(new int[]{x, y, z});
                break;
            }
            case MSG_ID_WIND: {
                int direction, speed;
                direction = ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
                speed = receivedBytes[4] & 0xFF;
                microMagic.setWind(direction, speed);
                break;
            }
            case MSG_ID_WATER_SPEED: {
                int speed = receivedBytes[2] & 0xFF;
                microMagic.setWaterSpeed(speed);
                break;
            }
            case MSG_ID_WATER_LEVEL: {
                int level = receivedBytes[2] & 0xFF;
                microMagic.setWaterLevel(level);
                break;
            }
            case MSG_ID_BATTERY: {
                int battery1 = receivedBytes[2] & 0xFF;
                int battery2 = receivedBytes[3] & 0xFF;
                microMagic.setBatteries(battery1, battery2);
                break;
            }
            case MSG_ID_GPS_DATA: {
                int latitude, trueHeading, groundSpeed, satCount;
                int longitude;
                latitude = (receivedBytes[5] & 0xFF) << 24 | (receivedBytes[4] & 0xFF) << 16
                        | (receivedBytes[3] & 0xFF) << 8 | (receivedBytes[2] & 0xFF);
                longitude = (receivedBytes[9] & 0xFF) << 24 | (receivedBytes[8] & 0xFF) << 16
                        | (receivedBytes[7] & 0xFF) << 8 | (receivedBytes[6] & 0xFF);
                trueHeading = (receivedBytes[11] & 0xFF) << 8 | (receivedBytes[10] & 0xFF);
                groundSpeed = receivedBytes[12] & 0xFF;
                satCount = receivedBytes[13] & 0xFF;
                microMagic.setGPSValues(latitude, longitude, trueHeading, groundSpeed, satCount);
                break;
            }
            case MSG_ID_GPS_RAW: {
                byte[] rawData = new byte[length - 1];
                System.arraycopy(receivedBytes, 2, rawData, 0, length - 1);
                microMagic.setGps_rawData(rawData);
                break;
            }
            case MSG_ID_SERVO_POSITIONS: {
                int rudder, mailsail, foresail;
                rudder = (short) ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
                mailsail = (short) ((receivedBytes[4] & 0xFF) | (receivedBytes[5] & 0xFF) << 8);
                foresail = (short) ((receivedBytes[6] & 0xFF) | (receivedBytes[7] & 0xFF) << 8);
                microMagic.setServos(new int[]{rudder, mailsail, foresail});
                break;
            }
            default:
                throw new ParseException("Unknown Message ID: " + Integer.toHexString(receivedBytes[1]), 1);
        }
    }
}
