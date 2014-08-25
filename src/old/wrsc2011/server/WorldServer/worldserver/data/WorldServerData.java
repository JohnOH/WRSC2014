/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldserver.data;

import java.util.*;

import worldserver.object.*;

/**
 *
 * @author Alex
 */
public class WorldServerData extends HashMap<String,WorldServerRecord> {

    public static HashSet<Class> getStandardFilter() {
        HashSet<Class> result = new HashSet<Class>();
        result.add(Buoy.class);
        result.add(SailBoat.class);
        result.add(PowerBoat.class);
        result.add(CircularObstacle.class);
        result.add(PolygonalObstacle.class);
        return result;
    }

    public WorldServerData getFilteredData(HashSet<Class> filter) {
        WorldServerData result = new WorldServerData();
        for (Map.Entry<String,WorldServerRecord> me : this.entrySet()) {
            for (Class c : filter)
                if (c.isInstance(me.getValue().source)) {
                    result.put(me.getKey(),new WorldServerRecord(me.getValue().source.getObjectForExport(),me.getValue().timeStamp));
                    continue;
                }
        }
        return result;
    }

}
