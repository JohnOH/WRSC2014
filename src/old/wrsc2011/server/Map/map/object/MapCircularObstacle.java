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
public class MapCircularObstacle extends MapCircularObject {

    public static Color standardColor = Color.gray;

    static {
        standardRadius = 500;
    } // cm

    public MapCircularObstacle(JXMapViewer jxMap, GeoPosition gp) {
        super(jxMap, gp);
        this.jObject.setColor(standardColor);        
    }
    
}
