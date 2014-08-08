/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleboat.control;

import java.util.*;
import javax.vecmath.*;

import map.*;
import map.object.*;

import worldserver.object.*;
import sampleboat.planning.*;

/**
 *
 * @author Administrator
 */
public class BoatControl extends Thread {

    public static final double BOAT_RADIUS = 400;
    public static final double BUOY_RADIUS = 200;
    public static final double OBSTACLE_RADIUS = 1000;    

    public volatile boolean stop = false;
    public volatile boolean active = false;
    public volatile int idleTime = 200;    

    protected RobSailBoat boat = null;
    protected SimpleMap simpleMap = null;
    protected long lastTimeStamp = 0;
    
    // temporary data, to avoid creating variables
    protected MicroMagic sailBoat = null;
    protected int[] servos = null;
    protected SensorData sd = new SensorData();
    protected ControlData cd = new ControlData();
    protected Vector<sampleboat.planning.Obstacle> obstacles = null;

    public BoatControl(RobSailBoat boat, SimpleMap simpleMap) {
        this.boat = boat;
        this.simpleMap = simpleMap;
        this.setName("BoatControl");              
    }

    public void run() {        
        while (!stop) {
            updateSensorData();
            doSmartStuff();
            updateControlData();
            this.simpleMap.addTrackPoint(sd.latGPS, sd.lonGPS);            
            idle();
        }       
    }
    
    protected void idle() {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doSmartStuff() {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void updateControlData() {        
            cd.rudder = 0;
            cd.jib = 0;
            cd.main = 0;
            setBoatData(cd.rudder, cd.main, cd.jib);        
    }
    
    protected void updateSensorData() {
        getBoatData();
        sd.timeStamp = lastTimeStamp;
        sd.id = sailBoat.getId();
        sd.team = sailBoat.getTeam();
        sd.speed = ((double) sailBoat.getGps_groundSpeed() * 18520d / 3600d); // speed in 1/10 kt, i.e., only 18520 instead of 185200 for cm/s
        sd.heading = ((double) sailBoat.getGps_trueHeading()) / 100d; // 1/100 deg
        sd.windSpeed = (double)sailBoat.getWindSpeed(); // a guess, not yet calibrated                
        sd.windDirection = ((double) sailBoat.getWindDirection()) / 10d; // 1/10 deg
        sd.latGPS = ((double) sailBoat.getGps_latitude()) / 10000000; 
        sd.lonGPS = ((double) sailBoat.getGps_longitude()) / 10000000; 
        int[] tmp = sailBoat.getAccelerometer();
        sd.accX = (double)tmp[0];
        sd.accY = (double)tmp[1];
        sd.accZ = (double)tmp[2];
        tmp = sailBoat.getMagnetometer();
        sd.magRawX = (double)tmp[0];
        sd.magRawY = (double)tmp[1];
        sd.magRawZ = (double)tmp[2];
        tmp = sailBoat.getMagHeading();
        sd.magY = (double)tmp[0] / 10d;
        sd.magP = (double)tmp[1] / 10d;
        sd.magR = (double)tmp[2] / 10d;
        tmp = sailBoat.getBatteries();
        sd.battery = ((double)tmp[0]) / 10d;
        tmp = sailBoat.getTurnRate();
        sd.gyroY = (double)tmp[0] / 10d;
        sd.gyroP = (double)tmp[1] / 10d;
        sd.gyroR = (double)tmp[2] / 10d;
        servos = sailBoat.getServos();
        if (servos != null) {
            sd.servo1 = servos[0];
            sd.servo2 = servos[1];
            sd.servo3 = servos[2];
        }
    }

    protected void getServerData() {
        obstacles = new Vector<sampleboat.planning.Obstacle>();
        for (MapBuoy mb : simpleMap.getMapBuoys())
            obstacles.add(new sampleboat.planning.CircularObstacle(simpleMap.getPosition(mb.getPosition()), new Vector2d(), mb.getRadius()));
        for (MapCircularObstacle mco : simpleMap.getMapObstacles())
            obstacles.add(new sampleboat.planning.CircularObstacle(simpleMap.getPosition(mco.getPosition()), new Vector2d(), mco.getRadius()));
        for (MapBoat mb : simpleMap.getMapBoats()) 
            if ((mb.getId() != null) && (boat != null) && (!mb.getId().equals(boat.getMAC())))
                obstacles.add(new sampleboat.planning.CircularObstacle(simpleMap.getPosition(mb.getPosition()), new Vector2d(), mb.getRadius()));
    }

    protected void getBoatData() {
        if (boat != null) try {
            lastTimeStamp = boat.getBoatStatus().timeStamp;
            sailBoat = (MicroMagic) boat.getBoatStatus().source;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setBoatData(double rudder, double main, double jib) {
        if ((boat != null) && (active)) try {
            ((RobSailBoat) boat).sendCommand(
                    new BoatCommand((int)(rudder*10), (int)(main*10), (int)(jib*10)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected WayPoint getNextWayPoint() {
        this.simpleMap.lock();
        MapWayPoint mwp = this.simpleMap.getMapWaypoints().iterator().next(); 
        WayPoint result = new WayPoint(this.simpleMap.getPosition(mwp.getPosition()), mwp.getRadius());
        this.simpleMap.unlock();
        return result;
    }

    public static class SensorData {
        public long timeStamp = 0;
        public String id = "";
        public String team = "";
        public double battery = 0;
        public double speed = 0;
        public double heading = 0;        
        public double windSpeed = 0;
        public double windDirection = 0;
        public double latGPS = 0;
        public double lonGPS = 0;
        public double accX = 0;
        public double accY = 0;
        public double accZ = 0;
        public double magRawX = 0;
        public double magRawY = 0;
        public double magRawZ = 0;
        public double magY = 0;
        public double magP = 0;
        public double magR = 0;
        public double gyroY = 0;
        public double gyroP = 0;
        public double gyroR = 0;
        public int servo1 = 0;
        public int servo2 = 0;
        public int servo3 = 0;
    }
        
    public static class ControlData {
        public double rudder = 0;
        public double jib = 0;
        public double main = 0;
    }
}
