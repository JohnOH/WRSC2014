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
public class MapBuoy extends MapCircularObject {

    public static Color standardColor = Color.yellow;
    
    protected static volatile long counter = 1;

    protected long number = 0;

    static {
        standardRadius = 200;
    } // cm

    public MapBuoy(JXMapViewer jxMap, GeoPosition gp) {
        super(jxMap, gp);
        this.number = counter++;
        this.jObject.setColor(standardColor);
        this.jObject.setToolTipText(number + ": " + gp.toString());
//        System.out.println("\t" + ((MapObjectPainter)jxMap.getOverlayPainter()).getWaypoints().size());
    }

    public long getNumber() {
        return this.number;
    }    

}
