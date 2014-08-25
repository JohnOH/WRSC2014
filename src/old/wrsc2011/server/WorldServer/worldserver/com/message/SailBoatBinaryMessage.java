package worldserver.com.message;

import java.text.*;
import worldserver.object.*;

public class SailBoatBinaryMessage implements BinaryMessageConstants, SailBoatBinaryMessageConstants {

    public static void parseAndSetValues(byte[] receivedBytes, SailBoat sailBoat)
            throws ParseException, NullPointerException {
        int length = receivedBytes[0] & 0xFF; // length = MSG_ID + payload
        if (receivedBytes.length != length + MSG_OVERHEAD)
            throw new ParseException("Length of given array does not match specified command length!", 0);
        if (receivedBytes[length + 1] != MSG_SEPERATOR)
            throw new ParseException("Message checksum (control symbol) is wrong!", length);
        switch (receivedBytes[1]) {
            case MSG_ID_WIND: {
                int direction, speed;
                direction = ((receivedBytes[2] & 0xFF) | (receivedBytes[3] & 0xFF) << 8);
                speed = receivedBytes[4] & 0xFF;
                sailBoat.setApparentWindDirection(((double)direction)/10);
                sailBoat.setApparentWindSpeed((speed));
                break;
            }
            case MSG_ID_GPS_DATA: {
                int latitude, longitude, heading, speed, satCount;
                latitude = (receivedBytes[5] & 0xFF) << 24 | (receivedBytes[4] & 0xFF) << 16
                        | (receivedBytes[3] & 0xFF) << 8 | (receivedBytes[2] & 0xFF);
                longitude = (receivedBytes[9] & 0xFF) << 24 | (receivedBytes[8] & 0xFF) << 16
                        | (receivedBytes[7] & 0xFF) << 8 | (receivedBytes[6] & 0xFF);
                heading = (receivedBytes[11] & 0xFF) << 8 | (receivedBytes[10] & 0xFF);
                speed = receivedBytes[12] & 0xFF;
                satCount = receivedBytes[13] & 0xFF;
                sailBoat.setLatitude(((double)latitude)/ 10000000d);
                sailBoat.setLongitude(((double)longitude)/ 10000000d);
                sailBoat.setHeading(((double)heading) / 100d);
                sailBoat.setSpeed(((double)speed) * 1852d / 3600d);
                break;
            }
            case MSG_ID_COM_CLOSED:
                // not handled
                break;
            case MSG_ID_COM_OPENED:
                if (length >= 15) { // dirty, check whether data available
                    String team = new String(receivedBytes, 2, 8);
                    long mac = (receivedBytes[10] & 0xFFL) | (receivedBytes[11] & 0xFFL) << 8
                            | (receivedBytes[12] & 0xFFL) << 16 | (receivedBytes[13] & 0xFFL) << 24
                            | (receivedBytes[14] & 0xFFL) << 32 | (receivedBytes[15] & 0xFFL) << 40;
                    sailBoat.setId(Long.toHexString(mac));
                    sailBoat.setTeam(team);
                }
                break;
            default:
                throw new ParseException("Unknown Message ID: " + Integer.toHexString(receivedBytes[1]), 1);
        }
    }

}
