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
public class JBorderedCircle extends JCircle {
   
    protected Color borderColor = Color.BLACK;

    public JBorderedCircle() {
        super();        
    }

    public JBorderedCircle(Color color, int radius) {
        super(color, radius);
    }

    public JBorderedCircle(Color color, int radius, Color borderColor) {
        super(color, radius);
        this.borderColor = borderColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        g.setColor(color);
        g.setStroke(stroke);
        if (active) g.fillOval(2,2,2*radius,2*radius); // 2,2 for stroke
        else g.drawOval(2,2,2*radius,2*radius); // 2,2 for stroke
        g.setColor(borderColor);
        g.drawOval(2,2,2*radius,2*radius); // 2,2 for stroke        
    }

}
