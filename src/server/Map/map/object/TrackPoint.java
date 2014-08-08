/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;

import org.jdesktop.swingx.mapviewer.*;

/**
 *
 * @author Alexander
 */
public class TrackPoint extends Waypoint {

    protected Color color = Color.black;

    protected static volatile long counter = 1;

    protected long id = 0;

    public TrackPoint(GeoPosition gp) {
        super(gp);
        this.id = counter++;
    }

    public TrackPoint(GeoPosition gp, Color color) {
        super(gp);
        this.id = counter++;
        this.color = color;
    }

    public long getID() {
        return this.id;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
