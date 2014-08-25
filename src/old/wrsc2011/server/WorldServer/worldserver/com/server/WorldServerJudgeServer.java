package worldserver.com.server;

import worldserver.*;
import java.io.*;
import java.net.*;

public class WorldServerJudgeServer extends WorldServerObjectServer {

    public static final int PREFERRED_PORT = 6005;

    public WorldServerJudgeServer(int port, WorldServer worldServer) {
        super(port, worldServer);
        System.out.println("WorldServerJudgeSocket listening on " + port);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new JudgeConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class JudgeConnectionHandler extends WorldServerObjectServer.ObjectConnectionHandler {

        public JudgeConnectionHandler(Socket socket) throws IOException {
            super(socket);            
        }

        protected boolean handleInput() {
            return true;
        }

    }

}
