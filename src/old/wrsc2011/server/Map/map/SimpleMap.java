/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map;

import java.util.*;
import java.util.concurrent.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import map.object.*;

/**
 *
 * @author Alex
 */
public class SimpleMap extends BasicMap {

    protected MapWayPoint homeWayPoint = null;

    protected Semaphore lock = new Semaphore(1,true);

    /** Creates new form SimpleMap */
    public SimpleMap() {
        super();
        initMouse();
        this.homeWayPoint = new MapWayPoint(this.jXMapKit1.getMainMap(),home);
    }

    public synchronized void lock() {
        try {
            //System.out.println(System.currentTimeMillis() + " " + lock.availablePermits());
            lock.acquire();
            //System.out.println(System.currentTimeMillis() + " " + lock.availablePermits());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void unlock() {
            //System.out.println(System.currentTimeMillis() + " " + lock.availablePermits());
        lock.release();
            //System.out.println(System.currentTimeMillis() + " " + lock.availablePermits());
    }

    public List<MapWayPoint> getMapWaypoints() {
        TreeMap<Long,MapWayPoint> tmp = new TreeMap<Long,MapWayPoint>();
        Set<Waypoint> wpSet = ((MapObjectPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet)
                if (wp instanceof MapWayPoint)
                    if (((MapWayPoint)wp).isActive()) tmp.put(new Long(((MapWayPoint)wp).getNumber()),(MapWayPoint)wp);
        }
        Vector<MapWayPoint> result = new Vector<MapWayPoint>(tmp.values());
        result.add(homeWayPoint);
        return result;
    }

    public List<MapBuoy> getMapBuoys() {
        TreeMap<String,MapBuoy> tmp = new TreeMap<String,MapBuoy>();
        Set<Waypoint> wpSet = ((MapObjectPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet)
                if (wp instanceof MapBuoy) tmp.put(new Long(((MapBuoy)wp).getNumber()).toString(),(MapBuoy)wp);
        }
        return new Vector<MapBuoy>(tmp.values());
    }

    public List<MapBoat> getMapBoats() {
        Vector<MapBoat> result = new Vector<MapBoat>();
        Set<Waypoint> wpSet = ((MapObjectPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet)
                if (wp instanceof MapBoat) result.add((MapBoat)wp);
        }
        return result;
    }

    public List<MapCircularObstacle> getMapObstacles() {
        Vector<MapCircularObstacle> result = new Vector<MapCircularObstacle>();
        Set<Waypoint> wpSet = ((MapObjectPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet)
                if (wp instanceof MapCircularObstacle) result.add((MapCircularObstacle)wp);
        }
        return result;
    }

    public MapBuoy addMapBuoy(String id, double lat, double lon, int radius) {
        return addMapBuoy(id, new GeoPosition(lat,lon),radius,0);
    }

    public MapBuoy addMapBuoy(String id, double lat, double lon, int radius, long timeStamp) {
        return addMapBuoy(id, new GeoPosition(lat,lon),radius,timeStamp);
    }

    public MapBuoy addMapBuoy(String id, GeoPosition gp, int radius) {
        return addMapBuoy(id, gp,radius,0);
    }

    public MapBuoy addMapBuoy(String id, GeoPosition gp, int radius, long timeStamp) {
        JXMapViewer mv = jXMapKit1.getMainMap();
        MapBuoy mb = new MapBuoy(mv, gp);
        mb.setRadius(radius);
        mb.getJObject().setLabel(id);
        mb.setId(id);
        mb.setLastTimeStamp(timeStamp);
        mv.repaint();
        return mb;
    }

    public MapBoat addMapBoat(String id, double lat, double lon, int radius) {
        return addMapBoat(id, new GeoPosition(lat,lon),radius,0);
    }

    public MapBoat addMapBoat(String id, double lat, double lon, int radius, long timeStamp) {
        return addMapBoat(id, new GeoPosition(lat,lon),radius,timeStamp);
    }

    public MapBoat addMapBoat(String id, GeoPosition gp, int radius) {
        return addMapBoat(id,gp,radius,0);
    }

    public MapBoat addMapBoat(String id, GeoPosition gp, int radius, long timeStamp) {
        JXMapViewer mv = jXMapKit1.getMainMap();
        MapBoat mb = new MapBoat(mv, gp);
        mb.setRadius(radius);
        mb.getJObject().setLabel(id);
        mb.setId(id);
        mb.setLastTimeStamp(timeStamp);
        mv.repaint();
        return mb;
    }

    public MapCircularObstacle addMapCircularObstacle(String id, double lat, double lon, int radius) {
        return addMapCircularObstacle(id, new GeoPosition(lat,lon),radius,0);
    }

    public MapCircularObstacle addMapCircularObstacle(String id, double lat, double lon, int radius, long timeStamp) {
        return addMapCircularObstacle(id, new GeoPosition(lat,lon),radius,timeStamp);
    }

    public MapCircularObstacle addMapCircularObstacle(String id, GeoPosition gp, int radius) {
        return addMapCircularObstacle(id, gp,radius,0);
    }

    public MapCircularObstacle addMapCircularObstacle(String id, GeoPosition gp, int radius, long timeStamp) {
        JXMapViewer mv = jXMapKit1.getMainMap();
        MapCircularObstacle mco = new MapCircularObstacle(mv, gp);
        mco.setRadius(radius);
        mco.getJObject().setLabel(id);
        mco.setId(id);
        mco.setLastTimeStamp(timeStamp);
        mv.repaint();
        return mco;
    }

    protected void initMouse() {
        this.jXMapKit1.getMainMap().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == evt.BUTTON1) {
                    JXMapViewer mv = jXMapKit1.getMainMap();
                    MapWayPoint mwp = new MapWayPoint(mv, mv.convertPointToGeoPosition(evt.getPoint()));
                    mwp.getJObject().setLabel(new Long(mwp.getNumber()).toString());
                    mv.repaint();
                    //System.out.println("New waypoint: " + mv.convertPointToGeoPosition(evt.getPoint()) + " Map: " + evt.getPoint() + " / " + mv.convertGeoPositionToPoint(mv.convertPointToGeoPosition(evt.getPoint())));
                }
            }
        });
    }

}
