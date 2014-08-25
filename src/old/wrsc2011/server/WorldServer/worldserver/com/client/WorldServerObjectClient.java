package worldserver.com.client;

import java.io.*;
import java.net.*;

public class WorldServerObjectClient extends WorldServerClient {
    
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;
    
    public WorldServerObjectClient(String ip, int port) {
        super(ip,port);
    }

    public boolean connect() {
        if (super.connect())
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(socket.getInputStream());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }

    public boolean disconnect() {
        try {
            oos.flush();
            oos.close();
            ois.close();
            return super.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObjectInputStream getObjectInputStream() {
        return this.ois;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return this.oos;
    }

}
