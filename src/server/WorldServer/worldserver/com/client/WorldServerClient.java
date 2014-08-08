package worldserver.com.client;

import java.io.*;
import java.net.*;

public class WorldServerClient implements Runnable {

    public volatile boolean stop = false;

    protected int port = 0;
    protected String ip = "";
    protected Socket socket = null;
    protected InputStream is;
    protected OutputStream os;

    public WorldServerClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new Socket(ip, port);
            socket.setKeepAlive(true);
            os = socket.getOutputStream();
            is = socket.getInputStream();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean disconnect() {
        try {
            socket.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void run() {
        while (!stop && socket.isConnected() && handleInput()) {
        }
    }

    public InputStream getInputStream() {
        return this.is;
    }

    public OutputStream getOutputStream() {
        return this.os;
    }

    protected boolean handleInput() {
        return true;
    }
}
