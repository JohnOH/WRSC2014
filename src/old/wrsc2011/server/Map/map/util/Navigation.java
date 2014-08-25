/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package map.util;

import javax.vecmath.*;

/**
 *
 * @author Administrator
 */
public class Navigation {

    public static Vector2d getSpeedVector(double angle, double length) {
        //System.out.println("\t\t\t" + angle + "\t" + Math.toRadians(-angle + 90) + "\t" + Math.sin(Math.toRadians(-angle + 90)));
        double x = Math.cos(Math.toRadians(-angle + 90)) * length; // -angle b/c of math direction, north up
        double y = Math.sin(Math.toRadians(-angle + 90)) * length; // -angle b/c of math direction, north up
        return new Vector2d(x,y);
    }

    public static Vector2d getWindVector(double angle, double length) {
        double x = Math.cos(Math.toRadians(-angle + 90)) * length * (-1); // -angle b/c of math direction
        double y = Math.sin(Math.toRadians(-angle + 90)) * length * (-1); // -angle b/c of math direction
        return new Vector2d(x,y);
    }

    public static double getWindDirection(double x, double y) {
        double tmp = Math.toDegrees(Math.atan2(-y, -x));
        return  (360 - (tmp - 90)) % 360;
    }


}
