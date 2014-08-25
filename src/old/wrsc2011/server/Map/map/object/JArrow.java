/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;
import javax.vecmath.*;

/**
 *
 * @author Alexander
 */
public class JArrow extends JObject {

    protected int radius = 8;

    protected Vector2d dir = new Vector2d(0,0);

    public JArrow(Vector2d dir) {
        super();
        this.dir = dir;
        init();
    }

    public JArrow(Color color, int radius, Vector2d dir) {
        super();
        this.setColor(color);
        this.setRadius(radius);
        this.dir = dir;
        init();
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.setSize(2*radius, 2*radius);
    }

    public Vector2d getDirection() {
        return this.dir;
    }

    public void setDirection(Vector2d dir) {
        this.dir = dir;
    }

    protected void init() {
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.stroke = new BasicStroke(3f);
    }

    protected void paintComponent(Graphics graphics) {
        this.setSize(2*radius, 2*radius);
        Graphics2D g = (Graphics2D)graphics;
        Vector2d tmp = new Vector2d(this.dir);
        tmp.scale(radius);
        g.setColor(color);
        g.drawOval(0,0,2*radius,2*radius);
        g.drawLine(radius, radius, radius + (int)tmp.x, radius + (int)tmp.y);
        //System.out.println("paint2 " + this.getBounds().toString() + "  " +  this.getLocation());
    }

}
