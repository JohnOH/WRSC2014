package sampleboat.control;

import java.util.*;

public class BoatCommand implements BoatComConstants {
	
	
	private short servo1 = 0; //Ruder
	private short servo2 = 0; //Grossegel
	private short servo3 = 0; //Fock

	/**
	 * Creates a BoatCommand Object, which can be send to a boat.
	 * Values for the servos are given in tenth of a degree, 0 is the mid-position,
	 * -900 means 90 degrees to the left in boat direction.
	 * @param rudder position for the rudder servo [-900 to 900]
	 * @param mainsail position for the mainsail servo [-900 to 900]
	 * @param foresail position for the foresail servo [-900 to 900]
	 * @throws IllegalArgumentException
	 */
	public BoatCommand(int rudder, int mainsail, int foresail) throws IllegalArgumentException {
		if (Math.abs(rudder) > 900) {
			throw new IllegalArgumentException("Rudder position must be between -900 and 900");
		}
		if (Math.abs(mainsail) > 900) {
			throw new IllegalArgumentException("Mainsail position must be between -900 and 900");
		}
		if (Math.abs(foresail) > 900) {
			throw new IllegalArgumentException("Foresail position must be between -900 and 900");
		}
		servo1 = (short) rudder;
		servo2 = (short) mainsail;
		servo3 = (short) foresail;
	}

        public void invert() {
            this.servo1 *= -1;
            this.servo2 *= -1;
            this.servo3 *= -1;
        }
	
	
	/**
	 * Method to get the binary command for setting the servo positions
	 * as set by the constructor.
	 * int values are stored in <b>little endian</b> format.
	 * @return an byte array representing the SetServos command, to be send to a boat.
	 */
	byte[] getSetServosCommandBytes() {
		byte[] cmdBytes = new byte[MSG_LEN_SET_SERVOS+MSG_OVERHEAD];
		int i = 0;
		cmdBytes[i++] = MSG_LEN_SET_SERVOS;
		cmdBytes[i++] = MSG_ID_SET_SERVOS;
		cmdBytes[i++] = (byte) (servo1 & 0xFF);
		cmdBytes[i++] = (byte) ((servo1>>8) & 0xFF);
		cmdBytes[i++] = (byte) (servo2 & 0xFF);
		cmdBytes[i++] = (byte) ((servo2>>8) & 0xFF);
		cmdBytes[i++] = (byte) (servo3 & 0xFF);
		cmdBytes[i++] = (byte) ((servo3>>8) & 0xFF);
		cmdBytes[i++] = MSG_SEPERATOR;
		return cmdBytes;
	}
	
	/**
	 * Method to get the binary command to ping a boat.
	 * @return a byte array respresenting the ping command, which can be send to a boat.
	 */
	static byte[] getPingCommandBytes() {
		byte[] cmdBytes = new byte[MSG_LEN_PING+MSG_OVERHEAD];
		int i = 0;
		cmdBytes[i++] = MSG_LEN_PING;
		cmdBytes[i++] = MSG_ID_PING;
		cmdBytes[i++] = MSG_SEPERATOR;
		return cmdBytes;
	}
	
	/**
	 * Method to get the binary command to close the connection to a boat.
	 * @return a byte array respresenting the close connection command, which can be send to a boat.
	 */
	static byte[] getComCloseCommandBytes() {
		byte[] cmdBytes = new byte[MSG_LEN_COM_CLOSE+MSG_OVERHEAD];
		int i = 0;
		cmdBytes[i++] = MSG_LEN_COM_CLOSE;
		cmdBytes[i++] = MSG_ID_COM_CLOSE;
		cmdBytes[i++] = MSG_SEPERATOR;
		return cmdBytes;
	}
	
	/**
	 * Method to get the binary command to establish a connection to a boat.
	 * @param team the name of the team, that requests the connection.
	 * @param mac_address a <code>long</code> containing the boats MAC address
	 * in the lowest 48 bits. 
	 * @return a byte array representing the open connection command, which can be send to a boat.
	 * @throws IllegalArgumentException if the team name is longer than 8 characters.
	 */
	static byte[] getComOpenCommandBytes(String team, long mac_address) throws IllegalArgumentException {
		if (team.length() > 8) {
			throw new IllegalArgumentException("maximal teamname length is 8 (was "+team.length()+")");
		}
		byte[] cmdBytes = new byte[MSG_LEN_COM_OPEN+MSG_OVERHEAD];
		int i = 0;
		cmdBytes[i++] = MSG_LEN_COM_OPEN;
		cmdBytes[i++] = MSG_ID_COM_OPEN;
		byte[] teamname = new byte[8];	//teamname must be exact 8 characters long
		// so we copy all (perhabs less than 8 chars) from the given name
		// if the name has less than 8 chars, the last bytes are 0x00
		System.arraycopy(team.getBytes(), 0, teamname, 0, team.getBytes().length);
		for (byte b : teamname) {
			cmdBytes[i++] = b;
		}
		cmdBytes[i++] = (byte) (mac_address & 0xFF);
		cmdBytes[i++] = (byte) (mac_address>>8 & 0xFF);
		cmdBytes[i++] = (byte) (mac_address>>16 & 0xFF);
		cmdBytes[i++] = (byte) (mac_address>>24 & 0xFF);
		cmdBytes[i++] = (byte) (mac_address>>32 & 0xFF);
		cmdBytes[i++] = (byte) (mac_address>>40 & 0xFF);
		cmdBytes[i++] = MSG_SEPERATOR;
		return cmdBytes;
	}
	
	/**
	 * Just for testing purposes...
	 * @param args ignored
	 */
	public static void main(String[] args) {
		System.out.println(Arrays.toString(getComOpenCommandBytes("TEST", 0x001FE1DB7F96L)));
	}
}
