package ya.haojun.roadtoadventure.model;

/**
 * Created by asus on 2017/3/8.
 */

public class GooglePoint {
    private float elevation;
    private GoogleLocation location;
    private float resolution;

    public GooglePoint() {
        location = new GoogleLocation();
    }

    public float getElevation() {
        return elevation;
    }

    public GoogleLocation getLocation() {
        return location;
    }

    public float getResolution() {
        return resolution;
    }

    @Override
    public String toString() {
        return "Point{" +
                "elevation=" + elevation +
                ", location=" + location +
                ", resolution=" + resolution +
                '}';
    }
}
