package sampleboat.control;

import java.io.*;
import java.text.*;
import java.util.*;

import worldserver.data.*;
import worldserver.object.*;

public class RobSailBoat implements Observer {

    public volatile boolean inverted = false;
    private final String identifier; // MAC Address or something like that
    public static final int QUERY_INTERVALL = 50; // ms
    private WorldServerRecord lastStatus;
    private boolean connected = false;
    private BoatConnectionInterface connection;
    private Object replyWait = new Object();
    private boolean waitForReply;
    protected String macAddress = new String();

    /**
     * Use this constructor to connect directly to a boat using a virtual serial
     * connection, or to connect to a "simulated" boat.
     *
     * @param id
     *            a String defining the endpoint for the communication:<br>
     *            if the String starts with "COM" <code>connect()</code> will
     *            try to connect to a boat via the given COM-Port<br>
     *            if it starts with "IP:" <code>connect()</code> will try to
     *            connect to a "simulated" boat via the given IP address.
     *            (format: "IP:X.X.X.X PORT:X")
     *
     *            To connect to the gate / Komm.-server use
     *            "IP:X.X.X.X PORT:X MAC:XXXXXXXXXXXX" whereby the mac address
     *            is given in hex
     */
    public RobSailBoat(String id) {
        identifier = id;
        lastStatus = new WorldServerRecord(new MicroMagic(), 0);
            String[] args = id.split(" ");
            if (args.length == 3) {
                String host = args[0].substring(3);
                int port = Integer.parseInt(args[1].substring(5));
                long macAddr = Long.parseLong(args[2].substring(4), 16);
                this.macAddress = Long.toHexString(macAddr);
                System.out.println("read MacAddr: " + macAddress);
                connection = new GateSocket(host, port, macAddr);
                connection.addObserver(this);
                waitForReply = true;
            } else if (args.length == 2) {
                // TODO Connect to simulator
            }
    }

    public String getMAC() {
        return this.macAddress;
    }

    /**
     * Tries to establish a connection to the boat represented by this object.
     * @param name the name to register at the gate-server.
     * @return true if the connection attempt was succesful, false otherwise.
     */
    public synchronized boolean connect(String name) {
        String user = name + "            ";
        user = user.substring(0, Math.min(user.length(), 8));
        try {
            if (connection.connect()) {
                if (connection instanceof GateSocket) {
                    connection.writeToStream(BoatCommand.getComOpenCommandBytes(user,
                            ((GateSocket) connection).macAddr));
                    if (waitForReply)
                        synchronized (replyWait) {
                            replyWait.wait(30000);
                        }
                    if (waitForReply)
                        connection.disconnect();
                }
                ((MicroMagic) lastStatus.source).setId(getMAC());
                ((MicroMagic) lastStatus.source).setTeam(user);
                return connected = !waitForReply;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connected = false;
    }

    /**
     * Tries to establish a connection to the boat represented by this object.
     * Uses the name of the current OS user account.
     *
     * @return true if the connection attempt was succesful, false otherwise.
     */
    public synchronized boolean connect() {
        return connect(System.getProperty("user.name"));
    }

    /**
     * Tests if the boat represented by this object responds to a
     * "ping"-message.
     *
     * @return true if an "ping"-respond message is received within 1000ms.
     */
    public synchronized boolean isConnected() {
        return connected && connection.isConnected();
    }

    /**
     * Sends a BoatCommand to the boat represented by this object.
     *
     * @param cmd
     *            the command to send.
     * @throws IOException
     *             if the method is called before a connection was established
     *             or if an IOException occurs while trying to send the command
     *             (e.g. connection is broken).
     */
    public synchronized void sendCommand(BoatCommand cmd) throws IOException {
        if (connected) {
            if (inverted) cmd.invert();
            byte[] cmdBytes = cmd.getSetServosCommandBytes();
            connection.writeToStream(cmdBytes);
        } else
            throw new IOException(
                    "Boat is not connected! Use connect() before trying to send commands.");
    }

    /**
     * Retrieves the latest status message that was received from the boat.
     *
     * @return a WorldServerRecord containing the latest status from the boat.
     * @throws IOException
     *             if the method is called before a connection was established
     *             or if an IOException occurs while trying to retrieve the
     *             status (e.g. connection is broken).
     */
    public synchronized WorldServerRecord getBoatStatus() throws IOException {
        if (connected)
            return lastStatus.clone();
        else
            throw new IOException(
                    "Boat is not connected! Use connect() before trying to get the status.");
    }

    /**
     * Disconnects this client from the boat represented by this object.
     */
    public synchronized void disconnect() {
        if (connected)
            try {
                connected = false;
                if (connection instanceof GateSocket)
                    connection.writeToStream(BoatCommand.getComCloseCommandBytes());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable instanceof BoatConnectionInterface && object instanceof byte[]) {
            byte[] data = (byte[]) object;
            try {
                if (data.length > 2 && data[1] == BoatComConstants.MSG_ID_COM_OPENED)
                    synchronized (replyWait) {
                        waitForReply = data[2] == 1; // wenn Fehler aufgetreten sind lassen wir
                        replyWait.notify(); // das flag gesetzt (s. connect())
                    }
                BoatMessage.parseAndSetValues(data, (MicroMagic) lastStatus.source);
                lastStatus = new WorldServerRecord(lastStatus.source, System.currentTimeMillis());
            } catch (ParseException e) {
                System.err.println(e.toString());
            }
        }
    }
}
