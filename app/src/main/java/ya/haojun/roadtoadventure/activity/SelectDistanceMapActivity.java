package ya.haojun.roadtoadventure.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.BitmapHelper;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.GoogleLeg;
import ya.haojun.roadtoadventure.model.GoogleRoute;
import ya.haojun.roadtoadventure.retrofit.GoogleMapService;
import ya.haojun.roadtoadventure.model.GoogleDirection;
import ya.haojun.roadtoadventure.helper.GoogleMapHelper;

public class SelectDistanceMapActivity extends CommonActivity implements OnMapReadyCallback, LocationListener, View.OnClickListener {

    // select status
    public static final int STATUS_NONE = 0;
    public static final int STATUS_FROM = 1;
    public static final int STATUS_TO = 2;
    // ui
    private TextView tv_from, tv_to;
    // map data
    private GoogleMap mMap;
    private GoogleDirection direction;
    private int selected = 0;
    // location data
    private LocationManager locationManager;
    private static final long MIN_TIME = 0;
    private static final float MIN_DISTANCE = 1;
    // data
    private int selectStatus = STATUS_NONE;
    private LatLng latLng_from, latLng_to;
    private Bitmap bmp_screenshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_distance_map);

        // ui reference
        tv_from = (TextView) findViewById(R.id.tv_select_distance_from);
        tv_to = (TextView) findViewById(R.id.tv_select_distance_to);
        findViewById(R.id.fab_select_distance_chart).setOnClickListener(this);
        findViewById(R.id.tv_select_distance_cancel).setOnClickListener(this);
        findViewById(R.id.tv_select_distance_confirm).setOnClickListener(this);

        // listener
        tv_from.setOnClickListener(this);
        tv_to.setOnClickListener(this);

        // sync map
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
        // request location
        requestLocationUpdate();

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
                if (selectStatus == STATUS_NONE) return;
                // check selected
                if (latLng_from != null && latLng_to != null) { // 'from','to' are ok
                    // show confirm ui
                    findViewById(R.id.ll_select_distance_confirm).setVisibility(View.VISIBLE);
                    findViewById(R.id.fab_select_distance_chart).setVisibility(View.VISIBLE);
                    // add 'to' marker
                    updateMapMarker();
                    // compute bounds
                    moveMap(latLng_from, latLng_to);
                    // load route
                    loadDirections(latLng_from, latLng_to);
                } else if (latLng_from != null) { // 'from' ok
                    updateMapMarker();
                    moveMap(latLng_from);
                }
                // reset status
                selectStatus = STATUS_NONE;
            }
        });
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
//                for (Polyline p : list_polyline) {
//                    p.setColor(p.equals(polyline) ? ContextCompat.getColor(SelectDistanceMapActivity.this, R.color.colorPrimary) : Color.GRAY);
//                }

                mMap.clear();
                updateMapMarker();
                moveMap(latLng_from, latLng_to);
                int w = (int) polyline.getWidth();
                // non selected
                for (int i = 0; i < direction.getRoutes().size(); i++) {
                    if (i == w - 16) continue;
                    GoogleRoute route = direction.getRoutes().get(i);
                    // get encoded string
                    String encodedString = route.getOverview_polyline().getPoints();
                    addPolyLineToMap(GoogleMapHelper.decodePoly(encodedString), Color.GRAY, i);
                }
                // selected
                for (int i = 0; i < direction.getRoutes().size(); i++) {
                    if (i == w - 16) {
                        GoogleRoute route = direction.getRoutes().get(i);
                        // get encoded string
                        String encodedString = route.getOverview_polyline().getPoints();
                        addPolyLineToMap(GoogleMapHelper.decodePoly(encodedString), ContextCompat.getColor(SelectDistanceMapActivity.this, R.color.colorPrimary), i);
                        selected = i;
                        break;
                    }
                }
                LogHelper.d("Selected:" + selected);
            }
        });
    }

    public void loadDirections(final LatLng from, final LatLng to) {

        Call<GoogleDirection> call = GoogleMapService.service.getDirections(GoogleMapHelper.getDirectionsQueryString(this, from, to));
        showLoadingDialog();
        call.enqueue(new Callback<GoogleDirection>() {
            @Override
            public void onResponse(Call<GoogleDirection> call, Response<GoogleDirection> response) {
                dismissLoadingDialog();
                direction = response.body();
                try {
                    for (int i = direction.getRoutes().size() - 1; i >= 0; i--) {
                        GoogleRoute route = direction.getRoutes().get(i);
                        // get encoded string
                        String encodedString = route.getOverview_polyline().getPoints();
                        // set polyline
                        List<LatLng> latLngs = GoogleMapHelper.decodePoly(encodedString);
                        addPolyLineToMap(latLngs, i == 0 ? ContextCompat.getColor(SelectDistanceMapActivity.this, R.color.colorPrimary) : Color.GRAY, i);
                    }
                    selected = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    t("取得路徑失敗");
                }
            }

            @Override
            public void onFailure(Call<GoogleDirection> call, Throwable t) {
                dismissLoadingDialog();
                t(String.format("取得路徑失敗(%s)", t.toString()));
            }
        });
    }

    private Polyline addPolyLineToMap(List<LatLng> list, int color, int position) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(list)
                .width(16 + position)
                .color(color)
                .geodesic(true);
        // add polyline to map
        Polyline polyline = mMap.addPolyline(polylineOptions);
        polyline.setClickable(true);
        return polyline;
    }

    private void requestLocationUpdate() {
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
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
        double lat = location.getLatitude();
        double lng = location.getLongitude();
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
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LogHelper.d(provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        LogHelper.d(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        LogHelper.d(provider);
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


    private void moveMap(LatLng from, LatLng to) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(from);
        builder.include(to);
        LatLngBounds bounds = builder.build();
        int padding = (int) (100 * getResources().getDisplayMetrics().density);
        // move camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
    }

    private GoogleRoute getSelectedRoute() {
        for (int i = 0; i < direction.getRoutes().size(); i++) {
            if (i == selected) {
                return direction.getRoutes().get(i);
            }
        }
        return null;
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
            case R.id.fab_select_distance_chart:
                GoogleRoute route = getSelectedRoute();
                if (route == null || route.getLegs().get(0) == null) {
                    t("No Route or Legs");
                    return;
                }
                GoogleLeg leg = route.getLegs().get(0);
                Bundle b = new Bundle();
                b.putString("summary", route.getSummary());
                b.putString("distance", leg.getDistance().getText());
                b.putString("duration", leg.getDuration().getText());
                b.putString("startAddress", leg.getStart_address());
                b.putString("endAddress", leg.getEnd_address());
                ArrayList<LatLng> list = new ArrayList<>();
                list.addAll(GoogleMapHelper.decodePoly(route.getOverview_polyline().getPoints()));
                b.putParcelableArrayList("routes", list);
                openActivity(ChartActivity.class, b);

                break;
            case R.id.tv_select_distance_confirm:
                moveMap(latLng_from, latLng_to);
                mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        File file = BitmapHelper.bitmap2JPGFile(SelectDistanceMapActivity.this, bitmap, "journey");
                        if (file == null) {
                            t("No Picture");
                            return;
                        }
                        GoogleRoute route = getSelectedRoute();
                        if (route == null || route.getOverview_polyline().getPoints() == null) {
                            t("No Route or Points");
                            return;
                        }

                        Intent intent = new Intent();
                        intent.putExtra("picturePath", file.getAbsolutePath());
                        intent.putExtra("points", route.getOverview_polyline().getPoints());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                break;
            case R.id.tv_select_distance_cancel:
                finish();
                break;
        }
    }
}
