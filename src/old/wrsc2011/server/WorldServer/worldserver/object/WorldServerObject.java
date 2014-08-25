package worldserver.object;

import java.io.Serializable;
import java.util.Random;

public class WorldServerObject implements Serializable, Cloneable {

    private static final long serialVersionUID = -6570255703426406205L;
    protected int pseudoID = new Random().nextInt();
    protected String id;
    protected double latitude;
    protected double longitude;

    public WorldServerObject() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public WorldServerObject getObjectForExport() {
        return this;
    }

    @Override
    public WorldServerObject clone() {
        try {
            return (WorldServerObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof WorldServerObject
                && ((WorldServerObject) obj).pseudoID == this.pseudoID;
    }

    @Override
    public String toString() {
        String result = new String();
        result = result.concat("ID:" + this.id + ";");
        result = result.concat("Lat:" + this.latitude + ";");
        result = result.concat("Lon:" + this.longitude + ";");
        return result;
    }

}
