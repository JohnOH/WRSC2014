package worldserver.com.server;

import java.io.*;
import java.net.*;

import worldserver.*;
import worldserver.data.*;
import worldserver.object.*;

public class WorldServerMapServer extends WorldServerObjectServer {

    public static final int PREFERRED_PORT = 6004;
    
    public WorldServerMapServer(int port, WorldServer worldServer) {
        super(port, worldServer);
        System.out.println("WorldServerMapSocket listening on " + port);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new MapConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
            System.out.println("WorldServerBoatSocket accepted connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class MapConnectionHandler extends WorldServerObjectServer.ObjectConnectionHandler {

        public MapConnectionHandler(Socket socket) throws IOException {
            super(socket);
            System.out.println("MapConnectionHandler accepted connection");
        }

        protected boolean handleInput() {
            try {
                WorldServerMessage wsm = (WorldServerMessage) this.ois.readObject();
                switch (wsm.messageID) {
                    case GET_SERVER_DATA:
                        oos.writeObject(new WorldServerMessage(WORLD_SERVER_DATA, worldServer.getData()));
                        break;
                    case PUT_SERVER_DATA:
                        if (wsm.messageObject instanceof WorldServerData) worldServer.putData((WorldServerData)wsm.messageObject);
                        break;
                    case UPDATE_DATA:
                        if (wsm.messageObject instanceof WorldServerObject) worldServer.update((WorldServerObject)wsm.messageObject);
                        break;
                    case REMOVE_DATA:
                        if (wsm.messageObject instanceof WorldServerObject) worldServer.remove((WorldServerObject)wsm.messageObject);
                        break;
                }
            } catch (SocketException e) {
                return false;
            } catch (EOFException e) {
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

    }

}
