/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

/**
 *
 * @author Alexander
 */
public class MapCircularObject extends MapObject {    

    public static int standardRadius = 500; // cm

    protected int radius = standardRadius;

    public MapCircularObject(JXMapViewer jxMap, GeoPosition gp) {
        super(jxMap, gp);
        this.setJObject(new JCircle());
        ((JCircle)this.jObject).setRadius(standardRadius);
    }

    public MapCircularObject(JXMapViewer jxMap, GeoPosition gp, boolean noInit) {
        super(jxMap, gp);
    }

    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
        this.setMapRadius((int)(this.radius / scalingFactor / 100)); // cm        
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    protected int getMapRadius() {
        return ((JCircle)this.jObject).getRadius();
    }

    protected void setMapRadius(int radius) {
        ((JCircle)this.jObject).setRadius(radius);
    }

}
