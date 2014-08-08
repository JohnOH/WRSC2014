package worldserver.com.client;

import java.io.*;
import java.util.*;

import worldserver.com.server.*;
import worldserver.data.*;
import worldserver.object.*;

public class WorldServerMapClient extends WorldServerDataClient {
    
    public WorldServerMapClient() {
        super("localhost",WorldServerMapServer.PREFERRED_PORT);
    }

    public WorldServerMapClient(String ip, int port) {
        super(ip,port);
    }

    public ArrayList<WorldServerRecord> getCurrentObjects() {
        WorldServerData wsd = this.getWorldServerData();
        ArrayList<WorldServerRecord> result = new ArrayList<WorldServerRecord>();
        result.addAll(wsd.values());
        return result;
    }

    public WorldServerRecord[] getCurrentWorldServerObjects() {
        return getCurrentObjects().toArray(new WorldServerRecord[0]);
    }

    public void update(WorldServerObject obj) {
        try {
            oos.writeObject(new WorldServerMessage(WorldServerMapServer.UPDATE_DATA, obj.clone()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(WorldServerObject obj) {
        try {
            oos.writeObject(new WorldServerMessage(WorldServerMapServer.REMOVE_DATA, obj.clone()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
