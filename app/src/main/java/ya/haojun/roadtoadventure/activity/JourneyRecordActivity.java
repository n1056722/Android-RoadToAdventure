package ya.haojun.roadtoadventure.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.JourneyModel;
import ya.haojun.roadtoadventure.model.LocationRecordModel;
import ya.haojun.roadtoadventure.sqlite.DAOLocationRecord;

public class JourneyRecordActivity extends CommonActivity implements OnMapReadyCallback, View.OnClickListener {

    // ui

    // map data
    private GoogleMap mMap;
    // extra
    private JourneyModel jm;
    //
    private ArrayList<LatLng> list_latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_record);
        // ui reference
        findViewById(R.id.fab_journey_record_info).setOnClickListener(this);
        // extra
        jm = getIntent().getExtras().getParcelable("journey");
        // sync map
        syncMap();
    }

    private void syncMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_journey_record);
        mapFragment.getMapAsync(this);
    }


    private void loadLocations() {
        // load locations
        DAOLocationRecord daoLocationRecord = new DAOLocationRecord(this);
        // compute bounds & convert to LatLng
        list_latLng = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LocationRecordModel l : daoLocationRecord.filter(jm.getStartTime(), jm.getStopTime())) {
            LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
            list_latLng.add(latLng);
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        int padding = (int) (100 * getResources().getDisplayMetrics().density);
        // move camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        // draw route
        mMap.addPolyline(new PolylineOptions()
                .addAll(list_latLng)
                .width(10)
                .color(ContextCompat.getColor(JourneyRecordActivity.this, R.color.colorPrimary))//Google maps blue color
                .geodesic(true)
        );
        // add marker
        if (!list_latLng.isEmpty()) {
            // start
            mMap.addMarker(new MarkerOptions()
                    .position(list_latLng.get(0))
                    .title("start")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            );
            // end
            mMap.addMarker(new MarkerOptions()
                    .position(list_latLng.get(list_latLng.size() - 1))
                    .title("end")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            );
        }
        // show info button
        findViewById(R.id.fab_journey_record_info).setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // connect Map Object
        mMap = googleMap;
        // load locations
        loadLocations();
    }

    private void showJourneyRecordInfo() {
        View v = getLayoutInflater().inflate(R.layout.dialog_journey_record_info, null);
        TextView tv_name = (TextView) v.findViewById(R.id.tv_dialog_journey_record_info_name);
        TextView tv_content = (TextView) v.findViewById(R.id.tv_dialog_journey_record_info_content);
        TextView tv_start_time = (TextView) v.findViewById(R.id.tv_dialog_journey_record_info_start_time);
        TextView tv_end_time = (TextView) v.findViewById(R.id.tv_dialog_journey_record_info_end_time);
        v.findViewById(R.id.tv_dialog_journey_record_info_chart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putParcelableArrayList("routes", list_latLng);
                openActivity(ChartActivity.class, b);
            }
        });
        // set text
        tv_name.setText(jm.getJourneyName());
        tv_content.setText(jm.getJourneyContent());
        tv_start_time.setText(jm.getStartTime());
        tv_end_time.setText(jm.getStopTime());
        alertWithView(v, null, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_journey_record_info:
                showJourneyRecordInfo();
                break;
        }
    }
}
