package worldserver.com.client;

import worldserver.com.server.*;
import worldserver.data.*;
import worldserver.object.*;

public class WorldServerBoatXMLClient extends WorldServerXMLClient {
    
    public WorldServerBoatXMLClient() {
        super("localhost",WorldServerBoatXMLServer.PREFERRED_PORT);
    }

    public WorldServerBoatXMLClient(String ip, int port) {
        super(ip,port);
    }

    public synchronized WorldServerData getWorldServerData() {
        try {
            this.xmlw.writeObject(new WorldServerMessage(WorldServerBoatXMLServer.GET_SERVER_DATA, new WorldServerData()));
            WorldServerMessage wsm = (WorldServerMessage) this.xmlr.readObject();
            if (wsm.getID() == WorldServerBoatXMLServer.WORLD_SERVER_DATA) {
                return (WorldServerData)wsm.getObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public synchronized void putWorldServerData(WorldServerData worldServerData) {
        try {
            this.xmlw.writeObject(new WorldServerMessage(WorldServerBoatXMLServer.PUT_SERVER_DATA, worldServerData));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void updateWorldServerData(WorldServerObject worldServerObject) {
        try {
            this.xmlw.writeObject(new WorldServerMessage(WorldServerBoatXMLServer.UPDATE_DATA, worldServerObject));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
