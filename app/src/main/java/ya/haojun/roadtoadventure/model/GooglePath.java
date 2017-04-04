package ya.haojun.roadtoadventure.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/3/8.
 */

public class GooglePath {
    private List<GooglePoint> results;
    private String status;

    public GooglePath() {
        results = new ArrayList<>();
    }

    public List<GooglePoint> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Paths{" +
                "results=" + results +
                ", status='" + status + '\'' +
                '}';
    }
}
