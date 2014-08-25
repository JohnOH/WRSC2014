/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sampleboat.gui;

import worldserver.legacy.*;
import worldserver.object.*;
import worldserver.data.*;

import map.*;

/**
 *
 * @author Administrator
 */
public class MapRefresher extends Thread {

    public static int TRACK_CNT = 1500; // 5 min

    public static int BOAT_RADIUS = 200;
    
    public volatile boolean stop = false;

    protected RobSailClient client = null;

    protected SimpleMap simpleMap = null;
   
    public MapRefresher (RobSailClient client, SimpleMap simpleMap) {
        this.client = client;
        this.simpleMap = simpleMap;
        this.setName("MapRefresher");
    }

    public void run() {        
        while (!stop) {
            updateMap();
            idle();
        }
    }

    protected void idle() {
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateMap() {        
        if (client != null) try {
            WorldServerRecord[] array = client.getCurrentWorldServerObjects();
            this.simpleMap.cleanMapObjects(map.object.MapBuoy.class);
            this.simpleMap.cleanMapObjects(map.object.MapCircularObstacle.class);
            this.simpleMap.cleanMapObjects(map.object.MapBoat.class);
            this.simpleMap.cleanTrackPoints(TRACK_CNT);
            for (WorldServerRecord rec : array) {
                double lat = rec.source.getLatitude();
                double lon = rec.source.getLongitude();
                if (rec.source instanceof SailBoat) {
                    this.simpleMap.addMapBoat(rec.source.getId(),lat,lon,BOAT_RADIUS,rec.timeStamp);
                }
                else if (rec.source instanceof worldserver.object.CircularObstacle)
                    this.simpleMap.addMapCircularObstacle(rec.source.getId(),lat,lon,(int)Math.round(((worldserver.object.CircularObstacle)rec.source).getRadius()));
                else if (rec.source instanceof worldserver.object.Buoy) 
                    this.simpleMap.addMapBuoy(rec.source.getId(),lat,lon,(int)Math.round(((worldserver.object.Buoy)rec.source).getRadius()));
            }
            this.simpleMap.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
