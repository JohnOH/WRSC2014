/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sampleboat.gui;

import worldserver.object.*;

import sampleboat.control.*;

/**
 *
 * @author Administrator
 */
public class TextRefresher extends Thread {

    public volatile boolean stop = false;

    protected RobSailBoat boat = null;

    protected javax.swing.JTextArea jTextArea = null;

    public TextRefresher (RobSailBoat boat, javax.swing.JTextArea jTextArea) {
        this.boat = boat;
        this.jTextArea = jTextArea;
        this.setName("TextRefresher");
    }

    public void run() {
        long lat = 0;
        long lon = 0;
        MicroMagic sailBoat = null;
        while (!stop) {
            if (boat.isConnected()) {
                try {
                    sailBoat = (MicroMagic)boat.getBoatStatus().source;
                    //System.out.println(sailBoat);
                    //if (jTextArea != null) jTextArea.setText(sailBoat.toString());
                    if (jTextArea != null) jTextArea.setText(
                        "\nAcc: \t" + pa(sailBoat.getAccelerometer()) +
                        "\nBatt: \t" + pa(sailBoat.getBatteries()) +
                        "\nHead: \t" + pa(sailBoat.getMagHeading()) +
                        "\nMag: \t" + pa(sailBoat.getMagnetometer()) +
                        "\nSAT: \t" + sailBoat.getGps_satCount() +
                        "\nSPD: \t" + sailBoat.getGps_groundSpeed() +
                        "\nHDG: \t" + sailBoat.getGps_trueHeading() +
                        "\nLAT: \t" + sailBoat.getGps_latitude() +
                        "\nLON: \t" + sailBoat.getGps_longitude() +
                        "\nWDIR: \t" + sailBoat.getWindDirection() +
                        "\nWSPD: \t" + sailBoat.getWindSpeed() +
                        "\nGyro: \t" + pa(sailBoat.getTurnRate()));
                    lat = sailBoat.getGps_latitude();
                    lon = sailBoat.getGps_longitude();
                    //sailBoat.
                    //scm.add
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String pa(int[] a) {
        String result = new String();
        for (int i : a) result = result.concat(" " + i);
        return result;
    }

}
