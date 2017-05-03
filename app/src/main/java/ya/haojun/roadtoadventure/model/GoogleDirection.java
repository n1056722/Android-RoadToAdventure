package ya.haojun.roadtoadventure.model;

import java.util.ArrayList;
import java.util.List;

public class GoogleDirection {
    private List<GoogleRoute> routes;

    public GoogleDirection() {
        routes = new ArrayList<>();
    }

    public List<GoogleRoute> getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "GoogleDirection{" +
                "routes=" + routes +
                '}';
    }
}
