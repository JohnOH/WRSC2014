/*
 * GPS.java
 *
 * Created on May 29, 2010, 12:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package map.util;

import org.jdesktop.swingx.mapviewer.*;

/**
 *
 * @author Alex
 *
 * see http://www.movable-type.co.uk/scripts/latlong.html
 */
public class GPS {

    public static final int DEGcm = 11112000;
    
    public static final int INTDEG = 100000;

    public static final double RADIUS = 6371000; // in m
    
    /** Creates a new instance of GPS */
    public GPS() {
    }
    
    public static int toM(long deg) {
        long tmp = deg * DEGcm / INTDEG;
        return (int)tmp;
    }

    public static double getBearing(GeoPosition gp1, GeoPosition gp2) {
        return getBearingDeg(gp1.getLatitude(), gp1.getLongitude(), gp2.getLatitude(), gp2.getLongitude());
    }
    
    public static double getBearingDeg(double lat1, double lon1, double lat2, double lon2) {
        return getBearingRad(Math.toRadians(lat1),Math.toRadians(lon1),Math.toRadians(lat2),Math.toRadians(lon2));
    }    
    
    public static double getBearingRad(double lat1, double lon1, double lat2, double lon2) {
        double dLon = lon2 - lon1;
        double y = Math.sin(dLon) * Math.cos(lat2);  
        double x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);  
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    public static double getDistance(GeoPosition gp1, GeoPosition gp2) {
        return getDistanceDeg(gp1.getLatitude(), gp1.getLongitude(), gp2.getLatitude(), gp2.getLongitude());
    }

    public static double getDistanceDeg(double lat1, double lon1, double lat2, double lon2) {
        return getDistanceRad(Math.toRadians(lat1),Math.toRadians(lon1),Math.toRadians(lat2),Math.toRadians(lon2));
    }
    
    public static double getDistanceRad(double lat1, double lon1, double lat2, double lon2) {
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +          
                Math.cos(lat1) * Math.cos(lat2) *           
                Math.sin(dLon/2) * Math.sin(dLon/2);
        return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)) * RADIUS;
    }

}
