package worldserver.legacy;

import java.io.*;
import java.util.*;

import worldserver.data.*;
import worldserver.com.client.*;
import worldserver.com.server.*;

/**
 * This class gives access to the services provided by the WorldServer.
 * @author Mucho
 *
 */
public final class RobSailClient extends WorldServerBoatClient {

    public RobSailClient() {
        super("localhost",WorldServerBoatServer.PREFERRED_PORT);
    }

    public RobSailClient(String ip, int port) {
        super(ip,port);
    }

    /**
     * Method to get all WorldServerObjects currently registered at the WorldServer.
     * @return an array of WorldServerRecords holding the newest entry
     * for each WorldServerObject.
     */
    public WorldServerRecord[] getCurrentWorldServerObjects() throws IOException {
        WorldServerData wsd = this.getWorldServerData();
        ArrayList<WorldServerRecord> result = new ArrayList<WorldServerRecord>();
        result.addAll(wsd.values());
        return result.toArray(new WorldServerRecord[0]);
    }

}
