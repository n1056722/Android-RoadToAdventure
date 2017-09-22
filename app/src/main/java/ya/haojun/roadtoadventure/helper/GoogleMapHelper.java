package ya.haojun.roadtoadventure.helper;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.LocationRecordModel;

/**
 * Created by asus on 2017/3/8.
 */

public class GoogleMapHelper {


    public static String getLocationsQueryString(List<LatLng> list) {
        StringBuilder sb = new StringBuilder();
        boolean f = true;
        for (LatLng l : list) {
            sb.append(f ? "" : "|").append(String.format("%f,%f", l.latitude, l.longitude));
            f = false;
        }
        return sb.toString();
    }

    public static Map<String, String> getDirectionsQueryString(Context context, LatLng from, LatLng to) {
        Map<String, String> map = new HashMap<>();
        map.put("key", context.getString(R.string.google_maps_key));
        map.put("origin", String.format("%f,%f", from.latitude, from.longitude));
        map.put("destination", String.format("%f,%f", to.latitude, to.longitude));
        map.put("alternatives", "true");
        map.put("avoid", "tolls|highways|ferries");
        map.put("mode", "driving");
        return map;
    }

    public static Map<String, String> getPlacesQueryString(Context context, LatLng location, String keyword) {
        Map<String, String> map = new HashMap<>();
        map.put("key", context.getString(R.string.google_maps_key));
        map.put("location", String.format("%f,%f", location.latitude, location.longitude));
        map.put("rankby", "distance");
        map.put("keyword", keyword);
        map.put("language", "zh-TW");
        return map;
    }


    public static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public static double distance(List<LocationRecordModel> list) {
        Location l1 = new Location("L1");
        Location l2 = new Location("L2");
        float sum = 0f;
        for (int i = 0; i < list.size() - 1; i++) {
            l1.setLatitude(list.get(i).getLatitude());
            l1.setLongitude(list.get(i).getLongitude());
            l2.setLatitude(list.get(i + 1).getLatitude());
            l2.setLongitude(list.get(i + 1).getLongitude());
            sum += l1.distanceTo(l2);
        }
        return sum;
    }

    public static double distance(double lat1, double lng1, double lat2, double lng2){
        Location l1 = new Location("L1");
        Location l2 = new Location("L2");
        l1.setLatitude(lat1);
        l1.setLongitude(lng1);
        l2.setLatitude(lat2);
        l2.setLongitude(lng2);
        return l1.distanceTo(l2);
    }
}
