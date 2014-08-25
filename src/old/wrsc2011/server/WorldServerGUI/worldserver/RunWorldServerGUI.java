/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldserver;

/**
 *
 * @author Alex
 */
public class RunWorldServerGUI {

    public static void main(String[] args) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new worldserver.gui.WorldServerGUI().setVisible(true);
            }
        });
    }

}
