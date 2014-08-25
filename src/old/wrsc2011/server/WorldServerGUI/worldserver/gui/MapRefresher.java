/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldserver.gui;

import worldserver.object.*;
import worldserver.data.*;
import worldserver.com.client.*;

import map.*;
import map.object.*;
import map.util.*;

/**
 *
 * @author Administrator
 */
public class MapRefresher extends Thread implements MapRemoveListener {

    public volatile boolean stop = false;

    protected WorldServerMapClient client = null;

    protected SimpleMap simpleMap = null;
   
    public MapRefresher (WorldServerMapClient client, SimpleMap simpleMap) {
        this.client = client;
        this.simpleMap = simpleMap;
    }

    public void run() {
        System.out.println("Running MapRefresher");
        while (!stop) {
            updateMap();
            idle();
        }
    }

    protected void idle() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateMap() {
        if (client != null) try {
            this.simpleMap.cleanMapObjects(map.object.MapBuoy.class);
            this.simpleMap.cleanMapObjects(map.object.MapCircularObstacle.class);
            this.simpleMap.cleanMapObjects(map.object.MapBoat.class);
            WorldServerRecord[] array = client.getCurrentWorldServerObjects();
            for (WorldServerRecord rec : array) {
                double lat = rec.source.getLatitude();
                double lon = rec.source.getLongitude();
                if (rec.source instanceof SailBoat) {
                    MapBoat mb = this.simpleMap.addMapBoat(rec.source.getId(),lat,lon,200);
                    mb.addMapRemoveListener(this);
                    //MapCircularObstacle mco = this.simpleMap.addMapCircularObstacle(rec.source.getId(),lat,lon,200);
                    //mco.addMapRemoveListener(this);
                }
                else if (rec.source instanceof worldserver.object.CircularObstacle) {
                    MapCircularObstacle mco = this.simpleMap.addMapCircularObstacle(rec.source.getId(),lat,lon,(int)Math.round(((worldserver.object.CircularObstacle)rec.source).getRadius()));
                    mco.addMapRemoveListener(this);
                }
                else if (rec.source instanceof worldserver.object.Buoy) {
                    MapBuoy mb = this.simpleMap.addMapBuoy(rec.source.getId(),lat,lon,(int)Math.round(((worldserver.object.Buoy)rec.source).getRadius()));
                    mb.addMapRemoveListener(this);
                }
//                System.out.println(rec.source + "  " + lat + "  " + lon);
            }
            this.simpleMap.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyRemove(MapObject mapObject) {
        System.out.println("Remove " + mapObject.getId());
        client.remove(new worldserver.object.EmptyObject(mapObject.getId()));
    }

}
