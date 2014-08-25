/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import map.util.*;

/**
 *
 * @author Alexander
 */
public class MapObject extends Waypoint implements MouseListener, MouseMotionListener {

    protected String id = "";

    protected JObject jObject = null;

    protected MapObjectPainter mop = null;

    protected JXMapViewer jxMap = null;

    protected boolean active = true;

    protected double scalingFactor = 1d;

    protected long lastTimeStamp = 0;

    protected HashSet<MapRemoveListener> removeListeners = new HashSet<MapRemoveListener>();

    protected MapObject(JXMapViewer jxMap, GeoPosition gp) {
        super(gp);
        this.jxMap = jxMap;
        this.mop = (MapObjectPainter)jxMap.getOverlayPainter();        
    }

    public MapObject(JXMapViewer jxMap, GeoPosition gp, JObject jObject) {
        super(gp);
        this.jxMap = jxMap;
        this.mop = (MapObjectPainter)jxMap.getOverlayPainter();
        this.jObject = jObject;
        this.jObject.addMouseListener(this);
        this.jObject.addMouseMotionListener(this);
        this.jObject.setToolTipText(gp.toString());
        this.jObject.setVisible(true);        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { addLater(); }
        });
        Set<Waypoint> wpSet = this.mop.getWaypoints();
        synchronized (wpSet) {
            wpSet.add(this);
        }        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastTimeStamp() {
        return lastTimeStamp;
    }

    public void setLastTimeStamp(long lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

    public JObject getJObject() {
        return this.jObject;
    }

    protected void setJObject(JObject jObject) {
        this.jObject = jObject;
        this.jObject.addMouseListener(this);
        this.jObject.addMouseMotionListener(this);
        this.jObject.setVisible(true);
        this.jxMap.add(jObject);
        Set<Waypoint> wpSet = this.mop.getWaypoints();
        synchronized (wpSet) {
            wpSet.add(this);
        }
    }

    public void addMapRemoveListener(MapRemoveListener mapRemoveListener) {
        this.removeListeners.add(mapRemoveListener);
    }

    public void removeMapRemoveListener(MapRemoveListener mapRemoveListener) {
        this.removeListeners.remove(mapRemoveListener);
    }

    public boolean isActive() {
        return active;
    }

    public double getScalingFactor() {
        return this.scalingFactor;
    }

    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == e.BUTTON3) {
            mop.getWaypoints().remove(this);
            jxMap.remove(jObject);
            jxMap.repaint();
            for (MapRemoveListener mrl : this.removeListeners) mrl.notifyRemove(this);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.setPosition(jxMap.convertPointToGeoPosition(e.getPoint()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void addLater() {
        jxMap.add(jObject);
    }

}
