package worldserver.com.client;

import java.io.*;
import java.net.*;

public class WorldServerStringClient extends WorldServerClient {
    
    protected BufferedReader br;
    protected BufferedWriter bw;
    
    public WorldServerStringClient(String ip, int port) {
        super(ip,port);
    }

    public boolean connect() {
        if (super.connect())
            try {                
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));                
                bw.flush();                
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //System.out.println("Client");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }

    public boolean disconnect() {
        try {
            bw.flush();
            bw.close();
            br.close();
            return super.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }    

}
