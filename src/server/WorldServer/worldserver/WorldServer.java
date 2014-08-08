package worldserver;


import java.util.*;

import worldserver.data.*;
import worldserver.io.*;
import worldserver.object.*;
import worldserver.com.server.*;

public class WorldServer extends Observable {

    public static final long START_TIME = System.currentTimeMillis();
    public WorldLogger logger;
    protected WorldServerData data = new WorldServerData();

    public WorldServer() {
        logger = new WorldLogger();        
    }

    public void start() {
        logger.start();
        (new Thread(new WorldServerGateServer(WorldServerGateServer.PREFERRED_PORT, this))).start();
        (new Thread(new WorldServerBoatServer(WorldServerBoatServer.PREFERRED_PORT, this))).start();
        (new Thread(new WorldServerMapServer(WorldServerMapServer.PREFERRED_PORT, this))).start();
        (new Thread(new WorldServerBoatStringServer(WorldServerBoatStringServer.PREFERRED_PORT, this))).start();
        (new Thread(new WorldServerBoatXMLServer(WorldServerBoatXMLServer.PREFERRED_PORT, this))).start();
    }

    public synchronized WorldServerData copyData() {
        WorldServerData result = new WorldServerData();
        try {
            for (Map.Entry<String,WorldServerRecord> me : data.entrySet()) result.put(new String(me.getKey()), (WorldServerRecord)me.getValue().clone());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized WorldServerData getData() {
        WorldServerData result = new WorldServerData();
        try {
            result = (WorldServerData)data.clone();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized WorldServerData getFilteredData() {
        WorldServerData result = data.getFilteredData(WorldServerData.getStandardFilter());
        return result;
    }

    public synchronized Vector<String> getStringData() {
        Vector<String> result = new Vector<String>();
        for (WorldServerRecord wsr : this.data.values()) {
            result.add(wsr.timeStamp + ";" + wsr.source.toString());
        }
        return result;
    }

    public synchronized void putData(WorldServerData data) {
        this.data.putAll(data);

    }

    @SuppressWarnings("unchecked")
    public synchronized ArrayList<WorldServerRecord> getCurrentObjects() {
        ArrayList<WorldServerRecord> result = new ArrayList<WorldServerRecord>();
        return result;
    }

    public void update(WorldServerObject obj) {
        WorldServerRecord rec = new WorldServerRecord(obj, System.currentTimeMillis());
        if (logger != null) logger.log(rec);
        synchronized (data) {
            data.put(obj.getId(), rec);
        }
        //System.out.println(data.size() + "  " + obj);
        setChanged();
        notifyObservers(rec);
    }

    public void remove(WorldServerObject obj) {
        synchronized (data) {
            data.remove(obj.getId());
        }
    }

    public static void main(String[] args) throws Exception {
        new WorldServer().start();
    }
}
