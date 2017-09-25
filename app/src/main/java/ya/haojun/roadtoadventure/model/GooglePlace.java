package ya.haojun.roadtoadventure.model;

/**
 * Created by rin84 on 2017/9/22.
 */

public class GooglePlace {

    private GoogleGeometry geometry;
    private String name;
    private String vicinity;

    public GoogleGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleGeometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
