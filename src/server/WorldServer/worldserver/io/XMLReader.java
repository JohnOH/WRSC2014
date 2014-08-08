/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldserver.io;

import java.io.*;
import java.beans.*;

/**
 *
 * @author Alex
 *
 * Note: XMLDecoder/XMLEncoder do not work via open sockets. Need to close
 * encoder stream to initialte decoding.
 *
 */
public class XMLReader {

    protected BufferedReader br = null;
    protected String line = null;
    protected XMLDecoder d = null;
    protected ByteArrayInputStream bais = null;

    public XMLReader(BufferedReader br) {
        this.br = br;
    }

    public synchronized Object readObject() {        
        String s = this.readXMLObject();
        System.out.println(s);
        return this.decodeObject(s);
    }

    protected Object decodeObject(String s) {
        Object result = null;
        bais = new ByteArrayInputStream(s.getBytes());
        d = new XMLDecoder(bais);
        result = d.readObject();
        d.close();
        return result;
    }

    protected String readXMLObject() {
        String result = new String();
        try {            
            line = br.readLine();
            while ((line != null) && (!line.trim().startsWith("<java"))) {
                line = br.readLine();
            }                        
            while ((line != null) && (!line.trim().startsWith("</java"))) {
                result = result.concat(line).concat("\n");
                line = br.readLine();
            }
            result = result.concat(line).concat("\n");
            //if (line == null) return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
