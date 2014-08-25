/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.object;

import java.awt.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import map.util.*;

/**
 *
 * @author Alexander
 */
public class MapBoat extends MapCircularObject {

    public static Color standardColor = Color.white;

    public static Color boatColor = Color.white;

    static {
        standardRadius = 500;
    } // cm

    public MapBoat(JXMapViewer jxMap, GeoPosition gp) {
        super(jxMap, gp, false);
        this.setJObject(new JBorderedCircle());
        ((JCircle)this.jObject).setRadius(standardRadius);
        this.jObject.setColor(standardColor);
          
    }

    public void setId(String id) {
        this.id = id;
        Color borderColor = boatColor;
        try {
            for (String[] s : Boats.data) 
                if (s[0].equals(this.id)) borderColor = getColor(s[2]);                
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((JBorderedCircle) this.jObject).setBorderColor(borderColor);        
    }

    protected void newJObject() {
        this.jObject = new JBorderedCircle();
    }

    protected Color getColor(String s) {
        if (s.startsWith("blue")) return Color.blue;
        if (s.startsWith("red")) return Color.red;
        if (s.startsWith("yellow")) return Color.yellow;
        if (s.startsWith("green")) return Color.green;
        if (s.startsWith("black")) return Color.black;
        return Color.white;
    }

}
