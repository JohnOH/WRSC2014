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
public class Obstacle {

    protected Point2d position = new Point2d();

    protected Vector2d velocity = new Vector2d();
        
    /** Creates a new instance of Obstacle */
    public Obstacle() {
    }
    
    /** Creates a new instance of Obstacle */
    public Obstacle(Point2d position, Vector2d velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Point2d getPosition() {
        return position;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public double getDistance(Point2d p) {
        return this.position.distance(p);
    }
    
}
