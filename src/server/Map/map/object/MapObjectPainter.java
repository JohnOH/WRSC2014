/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import map.*;

/**
 *
 * @author Alexander
 */
public class MapObjectPainter extends WaypointPainter<JXMapViewer> {

    protected BasicMap basicMap = null;

    public MapObjectPainter() {
        super();
    }

    public MapObjectPainter(BasicMap basicMap) {
        super();
        this.basicMap = basicMap;
    }

    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
        Set<Waypoint> wpSet = this.getWaypoints();
        synchronized (wpSet) {
            for (Waypoint wp : wpSet) {
                if (wp instanceof MapObject) paintMapObject(g, map, width, height, (MapObject)wp);
                else if(wp instanceof TrackPoint) paintTrackPoint(g, map, width, height, (TrackPoint)wp);
                else paintWaypoint(g, map, width, height, wp);
            }
        }
    }

    protected void paintWaypoint(Graphics2D g, JXMapViewer map, int width, int height, Waypoint wp) {
        Point2D gp_pt = map.getTileFactory().geoToPixel(wp.getPosition(), map.getZoom());
        Rectangle rect = map.getViewportBounds();
        Point pt = new Point((int) gp_pt.getX() - rect.x, (int) gp_pt.getY() - rect.y);
        g.setBackground(Color.black);
        g.setColor(Color.black);
        g.fillOval(pt.x - 1, pt.y - 1, 2, 2);
    }
    
    protected void paintTrackPoint(Graphics2D g, JXMapViewer map, int width, int height, TrackPoint wp) {
        Point2D gp_pt = map.getTileFactory().geoToPixel(wp.getPosition(), map.getZoom());
        Rectangle rect = map.getViewportBounds();
        Point pt = new Point((int) gp_pt.getX() - rect.x, (int) gp_pt.getY() - rect.y);
        g.setBackground(wp.getColor());
        g.setColor(wp.getColor());
        g.fillOval(pt.x - 1, pt.y - 1, 2, 2);
    }
    
    protected void paintMapObject(Graphics2D g, JXMapViewer map, int width, int height, MapObject mo) {
        if (this.basicMap != null) mo.setScalingFactor(this.basicMap.getScalingFactor());
        Point2D gp_pt = map.getTileFactory().geoToPixel(mo.getPosition(), map.getZoom());
        Rectangle rect = map.getViewportBounds();
        int w = mo.getJObject().getBounds().width;
        int h = mo.getJObject().getBounds().height;
        Point pt = new Point((int) gp_pt.getX() - rect.x - (w/2), (int) gp_pt.getY() - rect.y - (h/2));
        if (!pt.equals(mo.getJObject().getLocation())) mo.getJObject().setLocation(pt);
    }

}
