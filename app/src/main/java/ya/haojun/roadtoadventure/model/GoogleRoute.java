package ya.haojun.roadtoadventure.model;

import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;


public class GoogleRoute {
    private List<GoogleLeg> legs;
    private GooglePolyLine overview_polyline;
    private String summary;
    private Polyline polyline;

    public GoogleRoute(){
        legs = new ArrayList<>();
    }

    public List<GoogleLeg> getLegs() {
        return legs;
    }

    public GooglePolyLine getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(GooglePolyLine overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    @Override
    public String toString() {
        return "GoogleRoute{" +
                "legs=" + legs +
                ", overview_polyline=" + overview_polyline +
                ", summary='" + summary + '\'' +
                '}';
    }
}
