package worldserver.com.server;

import worldserver.*;
import java.io.*;
import java.net.*;

public class WorldServerServer implements Runnable {

    protected int port = 0;
    protected WorldServer worldServer = null;

    public WorldServerServer(int port, WorldServer worldServer) {
        this.port = port;
        this.worldServer = worldServer;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                newConnection(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new ConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class ConnectionHandler extends Thread {

        protected Socket socket;
        protected InputStream is;
        protected OutputStream os;

        public ConnectionHandler(Socket socket) throws IOException {
            super("ConnectionHandlerThread");
            this.socket = socket;
            this.is = socket.getInputStream();
            this.os = socket.getOutputStream();            
        }

        @Override
        public void run() {
            while (socket.isConnected() && handleInput()) {
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        protected boolean handleInput() {
            return true;
        }
        
    }

}
