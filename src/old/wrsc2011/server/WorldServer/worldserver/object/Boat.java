package worldserver.object;

public class Boat extends WorldServerObject {
    
    private static final long serialVersionUID = 6938270202495173167L;    
    protected double heading; // in deg
    protected double speed; // in m
    protected double radius; // in m
    
    public Boat() {
    }

    public Boat(String id) {
        this.id = id;        
    }
    
    public double getHeading() {
        return this.heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public Boat clone() {
        Boat clone = (Boat) super.clone();        
        return clone;
    }    

    @Override
    public String toString() {
        String result = super.toString();
        result = result.concat("Hdg:" + this.heading + ";");
        result = result.concat("Spd:" + this.speed + ";");
        result = result.concat("Radius:" + this.radius + ";");
        return result;
    }

}
