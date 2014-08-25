/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;
import java.util.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

/**
 *
 * @author Alexander
 */
public class MapTimerWayPoint extends MapWayPoint {

    protected Calendar endTime = null;

    public MapTimerWayPoint(JXMapViewer jxMap, GeoPosition gp, Calendar endTime) {
        super(jxMap, gp);
        this.endTime = endTime;
    }

    public boolean isActive() {
        boolean result = System.currentTimeMillis() < endTime.getTimeInMillis();
        if (!result) this.jObject.setActive(false);
        return result;
    }

}
