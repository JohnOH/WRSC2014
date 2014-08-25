package worldserver.object;

import java.awt.Polygon;

public class Obstacle extends WorldServerObject {

    private static final long serialVersionUID = 2636815942928382201L;
    protected static int count = 0;

    public Obstacle() {
    }

    public Obstacle(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        count++;
        this.id = "Obstacle" + count;
    }
    
}
