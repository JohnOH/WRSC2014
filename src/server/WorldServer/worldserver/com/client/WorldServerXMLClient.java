package worldserver.com.client;

import java.io.*;
import worldserver.io.*;

public class WorldServerXMLClient extends WorldServerStringClient {
    
    protected XMLReader xmlr;
    protected XMLWriter xmlw;
    
    public WorldServerXMLClient(String ip, int port) {
        super(ip,port);
    }

    public boolean connect() {
        if (super.connect())
            try {                
                xmlw = new XMLWriter(bw);
                xmlr = new XMLReader(br);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }

    public boolean disconnect() {
        try {            
            return super.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    

}
