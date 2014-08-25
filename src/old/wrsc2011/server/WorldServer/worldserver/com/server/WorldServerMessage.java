/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldserver.com.server;

import java.io.Serializable;

/**
 *
 * @author Alex
 */
public class WorldServerMessage implements Serializable {

    private static final long serialVersionUID = -6570255703426486207L;
    protected int messageID = 0;
    protected Object messageObject = null;

    public WorldServerMessage() {
    }

    public WorldServerMessage(int messageID, Object messageObject) {
        this.messageID = messageID;
        this.messageObject = messageObject;
    }

    public int getID() {
        return this.messageID;
    }

    public void setID(int messageID) {
        this.messageID = messageID;
    }

    public Object getObject() {
        return this.messageObject;
    }

    public void setObject(Object messageObject) {
        this.messageObject = messageObject;
    }
}
