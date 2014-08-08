/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;

/**
 *
 * @author Alexander
 */
public class JCircle extends JObject {

    protected int radius = 8;

    public JCircle() {
        super();        
        init();
    }

    public JCircle(Color color, int radius) {
        super();
        this.setColor(color);
        this.setRadius(radius);
        init();
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.setSize(2*radius+4, 2*radius+4); // +2*2 for stroke ...
    }

    protected void init() {
//        java.net.URL imageURL = this.getClass().getResource("images/buoy.png");
//        System.out.println(imageURL);
//        if (imageURL != null) {
//            setIcon(new javax.swing.ImageIcon(imageURL));
//        }
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.stroke = new BasicStroke(3f);
    }

    protected void paintComponent(Graphics graphics) {
//        Dimension d = new Dimension(2*radius, 2*radius);
//        if (!d.equals(this.getSize())) {
//            System.out.println(d + "  " + this.getSize());
//            this.setSize(d);
//        }
        Graphics2D g = (Graphics2D)graphics;
        g.setColor(color);
        g.setStroke(stroke);
        if (active) g.fillOval(2,2,2*radius,2*radius); // 2,2 for stroke
        else g.drawOval(2,2,2*radius,2*radius); // 2,2 for stroke
//        System.out.println("paint2 " + this.getBounds().toString() + "  " +  this.getLocation());
    }

}
