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
public class MapAutoWayPoint extends MapWayPoint {

    public MapAutoWayPoint(JXMapViewer jxMap, GeoPosition gp) {
        super(jxMap, gp);        
    }    
    
    public void setReached() {
        active = false;
        this.jObject.setActive(false);
    }
    
}
