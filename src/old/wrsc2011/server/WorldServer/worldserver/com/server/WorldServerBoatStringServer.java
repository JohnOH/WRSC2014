package worldserver.com.server;

import worldserver.*;
import java.io.*;
import java.net.*;

public class WorldServerBoatStringServer extends WorldServerStringServer {

    public static final int PREFERRED_PORT = 6006;
    
    public WorldServerBoatStringServer(int port, WorldServer worldServer) {
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

    protected class BoatConnectionHandler extends WorldServerStringServer.StringConnectionHandler {

        public BoatConnectionHandler(Socket socket) throws IOException {
            super(socket);            
        }

        protected boolean handleInput() {
            try {                
                String wsm = this.br.readLine();
                int messageID = Integer.parseInt(wsm);                
                switch (messageID) {
                    case GET_SERVER_DATA:
                        bw.write(new Integer(WORLD_SERVER_DATA).toString());
                        bw.newLine();
                        for (String s : worldServer.getStringData()) {
                            bw.write(s);
                            bw.newLine();
                        }
                        bw.newLine();
                        bw.flush();
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
