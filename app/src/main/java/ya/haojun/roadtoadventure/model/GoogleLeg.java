package ya.haojun.roadtoadventure.model;

public class GoogleLeg {
    private GoogleTextValue distance;
    private GoogleTextValue duration;
    private GoogleLatLng start_location;
    private GoogleLatLng end_location;
    private String start_address;
    private String end_address;

    public GoogleTextValue getDistance() {
        return distance;
    }

    public void setDistance(GoogleTextValue distance) {
        this.distance = distance;
    }

    public GoogleTextValue getDuration() {
        return duration;
    }

    public void setDuration(GoogleTextValue duration) {
        this.duration = duration;
    }

    public GoogleLatLng getStart_location() {
        return start_location;
    }

    public void setStart_location(GoogleLatLng start_location) {
        this.start_location = start_location;
    }

    public GoogleLatLng getEnd_location() {
        return end_location;
    }

    public void setEnd_location(GoogleLatLng end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }
}
