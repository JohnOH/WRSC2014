/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map;

import java.awt.event.*;
import org.jdesktop.swingx.*;

/**
 *
 * @author Alex
 */
public class InputMap extends SimpleMap {

    /** Creates new form SimpleMap */
    public InputMap() {
        super();
        initMouse();
    }

    public JXMapViewer getJXMapViewer() {
        return this.jXMapKit1.getMainMap();
    }

    public void addMouseListener(MouseListener mouseListener) {
        this.jXMapKit1.getMainMap().addMouseListener(mouseListener);
    }

    protected void initMouse() {
    }

}