package worldserver.com.client;

import java.io.*;
import java.net.*;

import worldserver.data.*;
import worldserver.com.server.*;

public class WorldServerDataClient extends WorldServerObjectClient {
    
    public WorldServerDataClient(String ip, int port) {
        super(ip,port);
    }

    public synchronized WorldServerData getWorldServerData() {
        try {
            oos.writeObject(new WorldServerMessage(WorldServerMapServer.GET_SERVER_DATA, null));
            oos.flush();
            WorldServerMessage wsm = (WorldServerMessage) ois.readObject();
            if (wsm.getID() == WorldServerBoatServer.WORLD_SERVER_DATA) {
                return (WorldServerData)wsm.getObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void putWorldServerData(WorldServerData worldServerData) {
        try {
            oos.writeObject(new WorldServerMessage(WorldServerMapServer.PUT_SERVER_DATA, worldServerData.clone()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
