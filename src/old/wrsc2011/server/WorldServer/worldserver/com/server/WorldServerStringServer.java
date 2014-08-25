package worldserver.com.server;

import worldserver.*;
import java.io.*;
import java.net.*;

public class WorldServerStringServer extends WorldServerServer {

    public static final int WORLD_SERVER_DATA = 1;
    public static final int GET_SERVER_DATA = 2;
    public static final int PUT_SERVER_DATA = 3;
    public static final int UPDATE_DATA = 4;
    public static final int REMOVE_DATA = 5;

    public WorldServerStringServer(int port, WorldServer worldServer) {
        super(port, worldServer);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new StringConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class StringConnectionHandler extends WorldServerServer.ConnectionHandler {

        protected BufferedReader br;
        protected BufferedWriter bw;

        public StringConnectionHandler(Socket socket) throws IOException {
            super(socket);
            this.bw = new BufferedWriter(new OutputStreamWriter(os));
            this.bw.flush();
            this.br = new BufferedReader(new InputStreamReader(is));
            //System.out.println("Server");
        }

        protected boolean handleInput() {
            return true;
        }

    }

}
