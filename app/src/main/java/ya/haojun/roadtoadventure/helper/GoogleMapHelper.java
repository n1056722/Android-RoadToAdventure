package ya.haojun.roadtoadventure.helper;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ya.haojun.roadtoadventure.R;

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

    /*
    *   @param origin
    *   @param destination
    *   @param avoid=tolls|highways|ferries
    *   @param mode=bicycling.....driving,walking,bicycling
    *   @param key
    * */
    public static Map<String, String> getDirectionsQueryString(Context context, LatLng from, LatLng to) {
        Map<String, String> map = new HashMap<>();
        map.put("origin", String.format("%f,%f", from.latitude, from.longitude));
        map.put("destination", String.format("%f,%f", to.latitude, to.longitude));
        map.put("avoid", "tolls|highways|ferries");
        map.put("mode", "driving");
        map.put("key", context.getString(R.string.google_maps_key));
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

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }
}