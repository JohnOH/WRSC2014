package worldserver.object;

import java.awt.Polygon;

// ToDo: save points as beans in collection to allow for XML serialization
public class PolygonalObstacle extends Obstacle {

    private static final long serialVersionUID = 2636815942928382201L;
    protected Polygon shape = null;

    public PolygonalObstacle() {
    }

    public PolygonalObstacle(double latitude, double longitude, Polygon shape) {
        super(latitude, longitude);
        this.shape = shape;
    }

    public Polygon getShape() {
        return this.shape;
    }

    public void setShape(Polygon shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        String result = super.toString();
        return result;
    }
}
