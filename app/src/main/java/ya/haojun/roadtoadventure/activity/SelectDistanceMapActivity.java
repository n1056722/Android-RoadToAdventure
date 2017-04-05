package ya.haojun.roadtoadventure.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.api.GoogleMapService;
import ya.haojun.roadtoadventure.model.GoogleDirection;
import ya.haojun.roadtoadventure.helper.GoogleMapHelper;

public class SelectDistanceMapActivity extends CommonActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener {

    // location status
    public static final int LOCATION_NONE = 0;
    public static final int LOCATION_MY_POSITION = 1;
    // select status
    public static final int STATUS_NONE = 0;
    public static final int STATUS_FROM = 1;
    public static final int STATUS_TO = 2;
    // ui
    private TextView tv_from, tv_to;
    // map data
    private GoogleMap mMap;
    // location data
    private LocationManager locationManager;
    private static final long MIN_TIME = 0;
    private static final float MIN_DISTANCE = 1;
    // data
    private int locationStatus = LOCATION_NONE;
    private int selectStatus = STATUS_NONE;
    private LatLng latLng_from, latLng_to;
    private List<LatLng> list_latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_distance_map);

        // ui reference
        tv_from = (TextView) findViewById(R.id.tv_select_distance_from);
        tv_to = (TextView) findViewById(R.id.tv_select_distance_to);
        findViewById(R.id.fab_select_distance).setOnClickListener(this);
        findViewById(R.id.tv_select_distance_cancel).setOnClickListener(this);
        findViewById(R.id.tv_select_distance_confirm).setOnClickListener(this);

        // listener
        tv_from.setOnClickListener(this);
        tv_to.setOnClickListener(this);

        // init data
        list_latLng = new ArrayList<>();
        // sync map
        syncMap();
    }

    private void syncMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_select_distance);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // connect Map Object
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        // check permission
        requestLocationUpdate(LOCATION_MY_POSITION);

        // listener
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // set latlng object
                switch (selectStatus) {
                    case STATUS_FROM:
                        latLng_from = latLng;
                        break;
                    case STATUS_TO:
                        latLng_to = latLng;
                        break;
                }
                // reset status
                selectStatus = STATUS_NONE;
                // check selected
                if (latLng_from != null && latLng_to != null) { // 'from','to' are ok
                    // show confirm ui
                    findViewById(R.id.ll_select_distance_confirm).setVisibility(View.VISIBLE);
                    findViewById(R.id.fab_select_distance).setVisibility(View.VISIBLE);
                    // add 'to' marker
                    updateMapMarker();
                    // compute bounds
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(latLng_from);
                    builder.include(latLng_to);
                    LatLngBounds bounds = builder.build();
                    int padding = (int) (100 * getResources().getDisplayMetrics().density);
                    // move camera
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                    // load route
                    loadDirections(latLng_from, latLng_to);
                } else if (latLng_from != null) { // 'from' ok
                    updateMapMarker();
                    moveMap(latLng_from);
                }
            }
        });
    }

    public void loadDirections(final LatLng from, final LatLng to) {
        // GoogleMapService is ready
        GoogleMapService service = GoogleMapService.retrofit.create(GoogleMapService.class);
        // getPaths is ready
        Call<GoogleDirection> call = service.getDirections(GoogleMapHelper.getDirectionsQueryString(this, from, to));
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
                    list_latLng.clear();
                    list_latLng.addAll(GoogleMapHelper.decodePoly(encodedString));
                    mMap.addPolyline(new PolylineOptions()
                            .addAll(list_latLng)
                            .width(10)
                            .color(ContextCompat.getColor(SelectDistanceMapActivity.this, R.color.colorPrimary))//Google maps blue color
                            .geodesic(true)
                    );
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

    private void requestLocationUpdate(int locationStatus) {
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        this.locationStatus = locationStatus;
        showLoadingDialog();
    }

    private void updateMapMarker() {
        mMap.clear();
        if (latLng_from != null) {
            mMap.addMarker(new MarkerOptions().position(latLng_from));
        }
        if (latLng_to != null) {
            mMap.addMarker(new MarkerOptions().position(latLng_to));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        dismissLoadingDialog();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        switch (locationStatus) {
            case LOCATION_MY_POSITION:
                // show map
                findViewById(R.id.pb_select_distance).setVisibility(View.GONE);
                findViewById(R.id.map_select_distance).setVisibility(View.VISIBLE);
                // init
                tv_from.setText("你的位置");
                latLng_from = new LatLng(lat, lng);
                updateMapMarker();
                // move map
                moveMap(new LatLng(lat, lng));
                locationManager.removeUpdates(this);
                break;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        log(provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        log(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        log(provider);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_distance_from:
                selectStatus = STATUS_FROM;
                t("選個起點吧");
                break;
            case R.id.tv_select_distance_to:
                selectStatus = STATUS_TO;
                t("選個終點吧");
                break;
            case R.id.fab_select_distance:
                Bundle b = new Bundle();
                ArrayList<LatLng> list = new ArrayList<>();
                list.addAll(list_latLng);
                b.putParcelableArrayList("routes", list);
                openActivity(ChartActivity.class, b);
                break;
            case R.id.tv_select_distance_confirm:
                Intent intent = new Intent();
                intent.putExtra("from", latLng_from);
                intent.putExtra("to", latLng_to);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_select_distance_cancel:
                finish();
                break;
        }
    }
}