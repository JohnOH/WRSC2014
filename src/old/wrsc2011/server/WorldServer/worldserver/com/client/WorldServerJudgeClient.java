package worldserver.com.client;

import worldserver.com.server.*;

public class WorldServerJudgeClient extends WorldServerDataClient {
    
    public WorldServerJudgeClient() {
        super("localhost",WorldServerJudgeServer.PREFERRED_PORT);
    }

    public WorldServerJudgeClient(String ip, int port) {
        super(ip,port);
    }

}
