package worldserver.com.server;

import worldserver.*;
import java.io.*;
import java.net.*;

public class WorldServerObjectServer extends WorldServerServer {

    public static final int WORLD_SERVER_DATA = 1;
    public static final int GET_SERVER_DATA = 2;
    public static final int PUT_SERVER_DATA = 3;
    public static final int UPDATE_DATA = 4;
    public static final int REMOVE_DATA = 5;

    public WorldServerObjectServer(int port, WorldServer worldServer) {
        super(port, worldServer);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new ObjectConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class ObjectConnectionHandler extends WorldServerServer.ConnectionHandler {

        protected ObjectInputStream ois;
        protected ObjectOutputStream oos;

        public ObjectConnectionHandler(Socket socket) throws IOException {
            super(socket);
            this.ois = new ObjectInputStream(is);            
            this.oos = new ObjectOutputStream(os);
            this.oos.flush();
        }

        protected boolean handleInput() {
            return true;
        }

    }

}
