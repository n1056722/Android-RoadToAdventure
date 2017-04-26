package ya.haojun.roadtoadventure.model;

/**
 * Created by asus on 2017/4/5.
 */

public class LocationRecordModel {
    private int LocationRecordId;
    private double latitude;
    private double longitude;
    private String time;

    public LocationRecordModel(){

    }

    public LocationRecordModel(double latitude, double longitude, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public int getLocationRecordId() {
        return LocationRecordId;
    }

    public void setLocationRecordId(int locationRecordId) {
        LocationRecordId = locationRecordId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
