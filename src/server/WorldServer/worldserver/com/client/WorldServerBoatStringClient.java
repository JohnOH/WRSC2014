package worldserver.com.client;

import worldserver.com.server.*;

public class WorldServerBoatStringClient extends WorldServerDataStringClient {
    
    public WorldServerBoatStringClient() {
        super("localhost",WorldServerBoatStringServer.PREFERRED_PORT);
    }

    public WorldServerBoatStringClient(String ip, int port) {
        super(ip,port);
    }

}
