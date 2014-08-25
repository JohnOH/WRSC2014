package worldserver.com.server;

import worldserver.*;
import worldserver.data.*;
import worldserver.object.*;
import java.io.*;
import java.net.*;

public class WorldServerBoatXMLServer extends WorldServerXMLServer {

    public static final int PREFERRED_PORT = 6009;
    public static volatile boolean restrictedAccess = false;

    public WorldServerBoatXMLServer(int port, WorldServer worldServer) {
        super(port, worldServer);
        System.out.println("WorldServerBoatXMLSocket listening on " + port);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new XMLBoatConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
            System.out.println("WorldServerBoatXMLSocket accepted connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class XMLBoatConnectionHandler extends WorldServerXMLServer.XMLStringConnectionHandler {

        public XMLBoatConnectionHandler(Socket socket) throws IOException {
            super(socket);
        }

        protected boolean handleInput() {
            try {
                WorldServerMessage wsm = (WorldServerMessage) this.xmlr.readObject();
                switch (wsm.messageID) {
                    case GET_SERVER_DATA:
                        this.xmlw.writeObject(new WorldServerMessage(WORLD_SERVER_DATA, worldServer.getFilteredData()));
                        break;
                    case PUT_SERVER_DATA:
                        if (wsm.messageObject instanceof WorldServerData) worldServer.putData((WorldServerData) wsm.messageObject);
                        break;
                    case UPDATE_DATA:
                        if (wsm.messageObject instanceof WorldServerObject)
                            if (!restrictedAccess || (SailBoat.class.isInstance(wsm.messageObject)))
                                worldServer.update((WorldServerObject) wsm.messageObject);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
