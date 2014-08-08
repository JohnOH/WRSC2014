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
public class XMLWriter {

    protected BufferedWriter bw = null;
    protected XMLEncoder e = null;
    protected ByteArrayOutputStream baos = null;

    public XMLWriter(BufferedWriter bw) {
        this.bw = bw;
    }

    public synchronized void writeObject(Object o) {
        String s = this.encodeObject(o);
        //System.out.println(s);
        this.writeXMLObject(s);
    }

    protected String encodeObject(Object o) {
        baos = new ByteArrayOutputStream();
        e = new XMLEncoder(baos);
        e.writeObject(o);
        e.close();
        return baos.toString();
    }

    protected void writeXMLObject(String s) {
        try {
            bw.write(s);
            bw.newLine();
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }

}
