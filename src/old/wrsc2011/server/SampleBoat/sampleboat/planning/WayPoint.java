/*
 * WayPoint.java
 *
 * Created on May 14, 2010, 6:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package sampleboat.planning;

import javax.vecmath.*;

/**
 *
 * @author Alex
 */
public class WayPoint {

    protected Point2d position = new Point2d();

    protected double radius = 0;
    
    public WayPoint() {
    }
    
    /** Creates a new instance of Obstacle */
    public WayPoint(Point2d position, double radius) {
        this.position = position;
        this.radius = radius;
    }        
    
    public Point2d getPosition() {
        return this.position;
    }

    public double getRadius() {
        return this.radius;
    }

    public String toString() {
        return new String("Position: " + position + " Radius: " + radius);
    }

}
