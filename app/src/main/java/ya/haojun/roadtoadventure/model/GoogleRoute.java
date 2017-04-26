package ya.haojun.roadtoadventure.model;

/**
 * Created by asus on 2017/3/11.
 */

public class GoogleRoute {
    private GooglePolyLine overview_polyline;

    public GooglePolyLine getPolyLine() {
        return overview_polyline;
    }

    @Override
    public String toString() {
        return "GoogleRoute{" +
                "overview_polyline=" + overview_polyline +
                '}';
    }
}
