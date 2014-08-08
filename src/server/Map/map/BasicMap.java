/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BasicMap.java
 *
 * Created on Nov 1, 2010, 4:42:19 PM
 */

package map;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.vecmath.*;
import org.jdesktop.swingx.mapviewer.*;

import map.util.*;
import map.object.*;

/**
 *
 * @author Alex
 */
public class BasicMap extends javax.swing.JPanel {

    protected final File tileChacheFile = new File(System.getProperty("user.home") + File.separator +"simplemap.tcf");

    protected GeoPosition refCenter = new GeoPosition(53.8727,10.6987); // Wakenitz/Falkenwiese

    protected GeoPosition home = new GeoPosition(53.8727,10.6987); // Wakenitz/Falkenwiese

    /** Creates new form SimpleMap */
    public BasicMap() {
        init();
        initOMSMap();
    }

    public void refresh() {
        this.jXMapKit1.repaint();
    }

    public GeoPosition getRefCenter() {
        return this.refCenter;
    }

    public void setRefCenter(GeoPosition refCenter) {
        this.refCenter = refCenter;
    }

    public GeoPosition getHome() {
        return this.home;
    }

    public void setHome(GeoPosition home) {
        this.home = home;
    }

    protected void initOMSMap() {
        TileFactoryInfo info = new TileFactoryInfo(
                0, // minimumZoomLevel
                17, // maximumZoomLevel
                18, // totalMapZoom
                256, //tile size
                true, true, // x/y orientation is normal
                "http://tile.openstreetmap.org", // baseURL
                "x", "y", "z" // URL params for x, y, z
                ) {

            public String getTileUrl(int x, int y, int z) {
                int zoom = 18 - z;
                String url = this.baseURL + "/" + zoom + "/" + x + "/" + y + ".png";
                return url;
            }
        };
        DefaultTileFactory tf = new DefaultTileFactory(info);
        tf.setTileCache(new DiskTileCache());
        this.jXMapKit1.setTileFactory(tf);
        this.jXMapKit1.getMainMap().setCenterPosition(this.refCenter);
        this.jXMapKit1.getMainMap().setZoom(1);
        this.jXMapKit1.getMainMap().setOverlayPainter(new MapObjectPainter(this));
    }

    public void addTrackPoint(double lat, double lon) {
        addTrackPoint(lat, lon, Color.black);
    }

    public void addTrackPoint(double lat, double lon, Color color) {
        Set<Waypoint> wpSet = ((WaypointPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            wpSet.add(new TrackPoint(new GeoPosition(lat,lon), color));
        }
    }

    public void addMapObject(MapObject mo) {
        Set<Waypoint> wpSet = ((WaypointPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            wpSet.add(mo);
        }
    }

    public void cleanMapObjects() {
        HashSet<Waypoint> tmp = new HashSet<Waypoint>();
        Set<Waypoint> wpSet = ((WaypointPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet)
                if (wp instanceof MapObject) {
                    tmp.add(wp);
                    cleanLater(((MapObject)wp).getJObject());
                }
            wpSet.removeAll(tmp);
        }
    }

    public void cleanMapObjects(Class c) {
        HashSet<Waypoint> tmp = new HashSet<Waypoint>();
        Set<Waypoint> wpSet = ((WaypointPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet)
                if (c.isInstance(wp)) {
                    tmp.add(wp);
                    if (wp instanceof MapObject) cleanLater(((MapObject)wp).getJObject());
                }
            wpSet.removeAll(tmp);
        }
    }

    public synchronized void cleanTrackPoints(int limit) {
        HashMap<Color,TreeMap<Long,TrackPoint>> pointsByColor = new HashMap<Color,TreeMap<Long,TrackPoint>>();
        TrackPoint tp = null;
        TreeMap<Long,TrackPoint> tmpPoints = null;
        Set<Waypoint> wpSet = ((WaypointPainter)this.jXMapKit1.getMainMap().getOverlayPainter()).getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet)
                if(wp instanceof TrackPoint) {
                    tp = (TrackPoint)wp;
                    tmpPoints = pointsByColor.get(tp.getColor());
                    if (tmpPoints == null) {
                        tmpPoints = new TreeMap<Long,TrackPoint>();
                        pointsByColor.put(tp.getColor(), tmpPoints);
                    }
                    tmpPoints.put(tp.getID(), tp);
                }
            for (TreeMap<Long,TrackPoint> tm : pointsByColor.values()) {
                long l = tm.values().size();
                long idx = 0;
                for (TrackPoint p : tm.values()) {
                    if ((l - idx++) > limit) wpSet.remove(p);
                }
            }
        }
    }

    public GeoPosition getGeoPosition(double x, double y) {
        Point2D.Double tmp = new Point2D.Double();
        Point2D p = this.jXMapKit1.getMainMap().convertGeoPositionToPoint(refCenter);
        tmp.x = p.getX() - (x / (this.getScalingFactor() * 100));
        tmp.y = p.getY() - (y / (this.getScalingFactor() * 100));
        return this.jXMapKit1.getMainMap().convertPointToGeoPosition(tmp);
    }

    public Point2d getCoordinates(double x, double y) {
        GeoPosition gp = getGeoPosition(x,y);
        return new Point2d(gp.getLatitude(),gp.getLongitude());
    }

    public Point2d getPosition(double lat, double lon) {
        Point2D p = this.jXMapKit1.getMainMap().convertGeoPositionToPoint(new GeoPosition(lat, lon));
        return new Point2d(getXRefDistanceCM(p.getX()),getYRefDistanceCM(p.getY()));
    }

    public Point2d getPosition(GeoPosition gp) {
        return getPosition(gp.getLatitude(), gp.getLongitude());
    }

    public double getXRefDistanceCM(double x) {
        double rx = this.jXMapKit1.getMainMap().convertGeoPositionToPoint(refCenter).getX();
        return (x - rx) * this.getScalingFactor() * 100; // positive x points east/right
    }

    public double getYRefDistanceCM(double y) {
        double ry = this.jXMapKit1.getMainMap().convertGeoPositionToPoint(refCenter).getY();
        return (ry - y) * this.getScalingFactor() * 100; // positive y points north/up (note that screen y points down)
    }

    public double getScalingFactor() {
        Point2D p1 = new Point2D.Double(0, 0);
        Point2D p2 = new Point2D.Double(100, 0);
        double d1 = p2.distance(p1);
        GeoPosition gp1 = this.jXMapKit1.getMainMap().convertPointToGeoPosition(p1);
        GeoPosition gp2 = this.jXMapKit1.getMainMap().convertPointToGeoPosition(p2);
        double d2 = GPS.getDistance(gp2, gp1);
        double f = d2 / d1;
        return f;
    }

    protected void init() {
        initComponents();
    }



    protected void cleanLater(final JObject jObject) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { cleanJObject(jObject); }
        });
    }

    protected void cleanJObject(JObject jObject) {
        this.jXMapKit1.getMainMap().remove(jObject);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXMapKit1 = new org.jdesktop.swingx.JXMapKit();

        jXMapKit1.setMiniMapVisible(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapKit1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapKit1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected org.jdesktop.swingx.JXMapKit jXMapKit1;
    // End of variables declaration//GEN-END:variables

}
