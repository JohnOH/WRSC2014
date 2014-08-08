/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Alexander
 */
public class JObject extends JButton {

    protected Color color = Color.BLACK;

    protected BasicStroke stroke = null;
   
    protected String label = "";

    protected boolean active = true;

    public JObject() {
        super();        
        init();
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.setToolTipText(label);
    }

    protected void init() {
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.stroke = new BasicStroke(3f);
    }    

}
