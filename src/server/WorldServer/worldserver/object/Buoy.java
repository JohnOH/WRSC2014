package worldserver.object;

public class Buoy extends WorldServerObject {

    private static final long serialVersionUID = -3195453730652196293L;
    private static int count = 0;
    protected double radius;

    public Buoy() {
    }

    public Buoy(double latitude, double longitude, double radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        count++;
        this.id = "Buoy" + count;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        String result = super.toString();
        result = result.concat("Radius:" + radius + ";");
        return result;
    }
    
}
