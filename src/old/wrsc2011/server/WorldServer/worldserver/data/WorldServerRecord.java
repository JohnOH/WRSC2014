package worldserver.data;

import java.io.*;
import worldserver.object.*;

/**
 * Objects of this class represent entries of the WorldServer.
 * They just hold an WorldServerObject and a timestamp.
 * @author Mucho
 *
 */
public class WorldServerRecord implements Serializable, Cloneable {

    private static final long serialVersionUID = -5650219179145240622L;
   
    public WorldServerObject source;
    
    public long timeStamp;

    public WorldServerRecord() {
        this.source = new WorldServerObject();
        this.timeStamp = Long.MIN_VALUE;
    }

    public WorldServerRecord(WorldServerObject source, long timeStamp) {
        this.source = source;
        this.timeStamp = timeStamp;
    }

    public WorldServerObject getSource() {
        return this.source;
    }

    public void setSource(WorldServerObject source) {
        this.source = source;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public WorldServerRecord clone() {
        WorldServerRecord rec = new WorldServerRecord((WorldServerObject) source.clone(), timeStamp);
        return rec;
    }

    @Override
    public String toString() {
        return "Record " + timeStamp + " source: "
                + source.getClass().getSimpleName() + " ID: " + source.getId()
                + " LAT: " + source.getLatitude() + " LON: "
                + source.getLongitude();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof WorldServerRecord
                && source == ((WorldServerRecord) obj).source;
    }
}
