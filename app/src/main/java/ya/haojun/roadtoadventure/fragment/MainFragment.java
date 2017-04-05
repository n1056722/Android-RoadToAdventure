package ya.haojun.roadtoadventure.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.api.GoogleMapService;
import ya.haojun.roadtoadventure.model.GoogleDirection;
import ya.haojun.roadtoadventure.helper.GoogleMapHelper;

/**
 * Created by asus on 2017/3/11.
 */

public class MainFragment extends CommonFragment implements OnMapReadyCallback, LocationListener {

    // location status
    public static final int LOCATION_NONE = 0;
    public static final int LOCATION_MY_POSITION = 1;
    // map data
    private GoogleMap mMap;
    // location data
    private LocationManager locationManager;
    private static final long MIN_TIME = 3000;
    private static final float MIN_DISTANCE = 1;
    // data
    private int status = LOCATION_NONE;

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // sync map
        syncMap();
    }

    private void syncMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_main);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // connect Map Object
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getMyActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMyActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        // check permission
        requestLocationUpdate(LOCATION_MY_POSITION);
    }

    private void requestLocationUpdate(int status) {
        if (locationManager == null)
            locationManager = (LocationManager) getMyActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getMyActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMyActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        this.status = status;
    }

    private void selectDistance(LatLng from, LatLng to) {
        // compute bounds
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(from);
        builder.include(to);
        LatLngBounds bounds = builder.build();
        int padding = (int) (100 * getResources().getDisplayMetrics().density);
        // move camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        // add marker
        addMarker(from);
        addMarker(to);
    }

    public void loadDirections(final LatLng from, final LatLng to) {
        // GoogleMapService is ready
        GoogleMapService service = GoogleMapService.retrofit.create(GoogleMapService.class);
        // getPaths is ready
        Call<GoogleDirection> call = service.getDirections(GoogleMapHelper.getDirectionsQueryString(getMyActivity(), from, to));
        showLoadingDialog();
        call.enqueue(new Callback<GoogleDirection>() {
            @Override
            public void onResponse(Call<GoogleDirection> call, Response<GoogleDirection> response) {
                dismissLoadingDialog();
                if (response.body() != null) {
                    log(response.body().toString());
                }
                try {
                    String encodedString = response.body().getRoutes().get(0).getPolyLine().getPoints();
                    List<LatLng> list = GoogleMapHelper.decodePoly(encodedString);
                    mMap.addPolyline(new PolylineOptions()
                            .addAll(list)
                            .width(10)
                            .color(ContextCompat.getColor(getMyActivity(), R.color.colorPrimary))//Google maps blue color
                            .geodesic(true)
                    );
                    selectDistance(from, to);
                } catch (Exception e) {
                    e.printStackTrace();
                    t("取得路徑失敗");
                }
            }

            @Override
            public void onFailure(Call<GoogleDirection> call, Throwable t) {
                dismissLoadingDialog();
                log(t.toString());
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        switch (status) {
            case LOCATION_MY_POSITION:
                moveMap(new LatLng(lat, lng));
                locationManager.removeUpdates(this);
                break;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void clearMarker() {
        mMap.clear();
    }

    private void addMarker(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng));
    }

    private void moveMap(LatLng place) {
        moveMap(place, false);
    }

    private void moveMap(LatLng place, boolean ani) {
        // build camera
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(place)
                        .zoom(16)
                        .build();
        // move
        if (ani) {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
