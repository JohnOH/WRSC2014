package sampleboat.control;

import java.io.*;
import java.util.*;

public class GateSocket extends Observable implements BoatConnectionInterface, BoatComConstants {

    public long macAddr = 0;

    protected GateClient gateClient = null;

    public GateSocket(String host, int port, long macAddr) {
        this.macAddr = macAddr; // legacy for RobSailBoat
        gateClient = new GateClient(host, port, macAddr, this);
    }

    @Override
    public synchronized boolean connect() {
        try {
            if (gateClient.connect()) {
                Thread thread = new Thread(gateClient);
                thread.setDaemon(true);
                thread.start();
                return gateClient.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized void disconnect() {
        gateClient.disconnect();
    }

    @Override
    public boolean isConnected() {
        return gateClient.isConnected();
    }

    @Override
    public synchronized void writeToStream(byte[] msg) throws IOException {
        gateClient.writeToStream(msg);
    }

    public void notifyNewData(byte[] data) {
        setChanged();
        notifyObservers(data);
    }
}
