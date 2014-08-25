package worldserver.com.server;

import worldserver.*;
import worldserver.io.*;
import java.io.*;
import java.net.*;

public class WorldServerXMLServer extends WorldServerStringServer {

    public WorldServerXMLServer(int port, WorldServer worldServer) {
        super(port, worldServer);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new XMLStringConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class XMLStringConnectionHandler extends WorldServerStringServer.StringConnectionHandler {

        protected XMLReader xmlr;
        protected XMLWriter xmlw;

        public XMLStringConnectionHandler(Socket socket) throws IOException {
            super(socket);
            this.xmlr = new XMLReader(br);
            this.xmlw = new XMLWriter(bw);
        }

        protected boolean handleInput() {
            return true;
        }

    }

}
