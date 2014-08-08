package worldserver.com.client;

import worldserver.com.server.*;

public class WorldServerBoatClient extends WorldServerDataClient {
    
    public WorldServerBoatClient() {
        super("localhost",WorldServerBoatServer.PREFERRED_PORT);
    }

    public WorldServerBoatClient(String ip, int port) {
        super(ip,port);
    }

}
