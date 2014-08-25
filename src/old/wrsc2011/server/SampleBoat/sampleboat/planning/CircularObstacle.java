/*
 * Obstacle.java
 *
 * Created on May 14, 2010, 6:27 PM
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
public class CircularObstacle extends Obstacle {

    protected double radius = 0;
        
    /** Creates a new instance of Obstacle */
    public CircularObstacle() {
        super();
    }
    
    /** Creates a new instance of Obstacle */
    public CircularObstacle(Point2d position, Vector2d velocity, double radius) {
        super(position, velocity);
        this.radius = radius;
    }    

    public double getRadius() {
        return this.radius;
    }

    public double getDistance(Point2d p) {
        double d = this.radius - this.position.distance(p);
        // rather hard boundary b/c of squarde distance from radius
        if (d>0) return d;
        else return 0;
    }

}
