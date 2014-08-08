package worldserver.com.server;

import worldserver.*;
import java.io.*;
import java.net.*;

public class WorldServerBoatServer extends WorldServerObjectServer {

    public static final int PREFERRED_PORT = 6003;    
    
    public WorldServerBoatServer(int port, WorldServer worldServer) {
        super(port, worldServer);
        System.out.println("WorldServerBoatSocket listening on " + port);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new BoatConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
            System.out.println("WorldServerBoatSocket accepted connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class BoatConnectionHandler extends WorldServerObjectServer.ObjectConnectionHandler {

        public BoatConnectionHandler(Socket socket) throws IOException {
            super(socket);            
        }

        protected boolean handleInput() {
            try {
                WorldServerMessage wsm = (WorldServerMessage) this.ois.readObject();
                switch (wsm.messageID) {
                    case GET_SERVER_DATA:
                        oos.writeObject(new WorldServerMessage(WORLD_SERVER_DATA, worldServer.getData()));
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
