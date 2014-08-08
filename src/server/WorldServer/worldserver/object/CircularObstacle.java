package worldserver.object;

public class CircularObstacle extends Obstacle {

    private static final long serialVersionUID = 2636815942928382209L;
    protected double radius;

    public CircularObstacle() {
    }

    public CircularObstacle(double latitude, double longitude, double radius) {
        super(latitude, longitude);
        this.radius = radius;
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
        result = result.concat("Radius:" + this.radius + ";");
        return result;
    }
}
