/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WolrdServerGUI.java
 *
 * Created on Sep 28, 2010, 1:46:16 PM
 */

package map;

import java.io.*;
import java.util.*;
import java.text.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import map.*;
import map.object.*;
import map.util.*;

/**
 *
 * @author Alex
 */
public class InputFrame extends javax.swing.JFrame {

    protected InputMap inputMap = null;
    
    /** Creates new form WolrdServerGUI */
    public InputFrame() {
        initComponents();
        InputMapFrame imf = new InputMapFrame();
        this.inputPanel1.setInputMap(imf.getInputMap());
        imf.setVisible(true);        
    }

    public InputFrame(InputMap inputMap) {
        initComponents();
        this.inputPanel1.setInputMap(inputMap);
    }

    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        inputPanel1 = new map.InputPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InputFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private map.InputPanel inputPanel1;
    // End of variables declaration//GEN-END:variables

}
