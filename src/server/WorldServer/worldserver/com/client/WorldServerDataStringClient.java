package worldserver.com.client;

import java.util.*;

import worldserver.com.server.*;

public class WorldServerDataStringClient extends WorldServerStringClient {
    
    public WorldServerDataStringClient(String ip, int port) {
        super(ip,port);
    }

    public synchronized Vector<String> getWorldServerData() {
        Vector<String> result = new Vector<String>();
        try {            
            bw.write(new Integer(WorldServerBoatStringServer.GET_SERVER_DATA).toString());
            bw.newLine();
            bw.flush();            
            String wsm = br.readLine();
            String data = br.readLine();            
            if (Integer.parseInt(wsm) == WorldServerBoatServer.WORLD_SERVER_DATA) {
                while (data.length() > 0) {
                    result.add(data);
                    data = br.readLine();
                }          
                return result;
            }
            else while (data.length() > 0) data = br.readLine(); // better check for new request?!
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
