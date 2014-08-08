package sampleboat.control;

import java.io.*;
import java.util.*;

import worldserver.com.client.*;

public class GateClient extends WorldServerClient implements BoatComConstants {

    protected long macAddress;
    protected GateSocket gateSocket = null;

    public GateClient(String ip, int port, long macAddress, GateSocket observable) {
        super(ip, port);
        this.macAddress = macAddress;
        this.gateSocket = observable;
    }

    @Override
    public void run() {
        while (socket.isConnected() && handleInput()) {
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public synchronized void writeToStream(byte[] msg) throws IOException {
        os.write(msg);
        os.flush();
    }

    protected boolean handleInput() {
        List<Byte> byteBuffer = new LinkedList<Byte>();
        int c;
        int length = 0;
        try {
            while ((c = is.read()) > -1 && this.isConnected()) {
                if (byteBuffer.size() == 0) {
                    length = c; // if buffer empty, start new message
                }
                byteBuffer.add(Byte.valueOf((byte) c)); // buffer every byte
                while (byteBuffer.size() >= length + MSG_OVERHEAD) {
                    if (byteBuffer.get(length + MSG_OVERHEAD - 1) == MSG_SEPERATOR) {
                        byte[] readBytes = new byte[length + MSG_OVERHEAD];
                        for (int i = 0; i < readBytes.length; i++) {
                            readBytes[i] = byteBuffer.get(0).byteValue();
                            byteBuffer.remove(0);
                        }
                        gateSocket.notifyNewData(readBytes);
                    } else { // if wrong message separator
                        byteBuffer.remove(0);
                        length = byteBuffer.get(0); // take first byte as new message start
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
