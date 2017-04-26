package ya.haojun.roadtoadventure.model;

/**
 * Created by asus on 2017/3/8.
 */

public class GoogleLocation {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
