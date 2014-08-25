package worldserver.com.server;

import worldserver.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

import worldserver.com.message.*;
import worldserver.object.*;

public class WorldServerGateServer extends WorldServerServer {

    public static final int PREFERRED_PORT = 6002;

    public WorldServerGateServer(int port, WorldServer worldServer) {
        super(port, worldServer);
        System.out.println("WorldServerGateSocket listening on " + port);
    }

    protected void newConnection(Socket socket) {
        try {
            Thread handler = new GateConnectionHandler(socket);
            handler.setDaemon(true);
            handler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class GateConnectionHandler extends WorldServerServer.ConnectionHandler implements BinaryMessageConstants {

        protected List<Byte> byteBuffer = new LinkedList<Byte>();
        protected int c;
        protected int length = 0;
        protected MicroMagic microMagic;

        public GateConnectionHandler(Socket socket) throws IOException {
            super(socket);
            microMagic = new MicroMagic();
        }

        protected boolean handleInput() {
            try {
                if (!((c = is.read()) > -1)) return false;
                // if buffer empty we start new message, first byte is message length
                if (byteBuffer.size() == 0) length = c;
                byteBuffer.add(Byte.valueOf((byte) c)); // buffer every byte
                while (byteBuffer.size() >= length + MSG_OVERHEAD)
                    if (byteBuffer.get(length + MSG_OVERHEAD - 1) == MSG_SEPERATOR) {
                        byte[] readBytes = new byte[length + MSG_OVERHEAD];
                        for (int i = 0; i < readBytes.length; i++) {
                            readBytes[i] = byteBuffer.get(0).byteValue();
                            byteBuffer.remove(0);
                        }
                        MicroMagicBinaryMessage.parseAndSetValues(readBytes, microMagic);
                        worldServer.update(microMagic.clone());
                    } else { // if wrong message separator, take first byte as length
                        byteBuffer.remove(0);
                        length = byteBuffer.get(0);
                    }
            } catch (SocketException e) {
                return false;
            } catch (EOFException e) {
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
