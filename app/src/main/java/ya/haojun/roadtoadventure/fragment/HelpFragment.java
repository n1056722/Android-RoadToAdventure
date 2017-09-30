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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.activity.JourneyStatusActivity;
import ya.haojun.roadtoadventure.adapter.HelpRVAdapter;
import ya.haojun.roadtoadventure.helper.GoogleMapHelper;
import ya.haojun.roadtoadventure.model.GoogleDirection;
import ya.haojun.roadtoadventure.model.GooglePlace;
import ya.haojun.roadtoadventure.model.GooglePlaces;
import ya.haojun.roadtoadventure.model.HelpShop;
import ya.haojun.roadtoadventure.model.LocationRecordModel;
import ya.haojun.roadtoadventure.retrofit.GoogleMapService;
import ya.haojun.roadtoadventure.sqlite.DAOJourney;
import ya.haojun.roadtoadventure.sqlite.DAOLocationRecord;


public class HelpFragment extends CommonFragment {

    // type
    public static final int HOSPITAL = 0;
    public static final int HOTEL = 1;
    public static final int BIKE_SHOP = 2;
    public static final int CONVENIENT_SHOP = 3;

    // ui
    private SwipeRefreshLayout srl;
    private RecyclerView rv;
    // extra
    private int type;
    private String keyword;
    // data
    private ArrayList<HelpShop> list;

    public static HelpFragment getInstance(int type) {
        HelpFragment f = new HelpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        srl = (SwipeRefreshLayout) v.findViewById(R.id.srl_fragment_help);
        rv = (RecyclerView) v.findViewById(R.id.rv_fragment_help);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getArguments().getInt("type");
        keyword = getKeyword();

        // init
        list = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new HelpRVAdapter(getActivity(), list));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                getPlaces();
            }
        });
        srl.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        getPlaces();
    }

    private String getKeyword() {
        if (type == HOSPITAL) return "醫院";
        if (type == HOTEL) return "旅社";
        if (type == BIKE_SHOP) return "腳踏車店";
        return "便利商店";
    }

    private void getPlaces() {
        // get last location
        LocationRecordModel l = new DAOLocationRecord(getActivity()).getLast();
        final LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());

        Call<GooglePlaces> call = GoogleMapService.service.getPlaces(GoogleMapHelper.getPlacesQueryString(getActivity(), latLng, keyword));
        srl.setRefreshing(true);
        call.enqueue(new Callback<GooglePlaces>() {
            @Override
            public void onResponse(Call<GooglePlaces> call, Response<GooglePlaces> response) {
                srl.setRefreshing(false);
                if (isResponseOK(response)) {
                    GooglePlaces result = response.body();
                    list.clear();
                    for (GooglePlace p : result.getResults()) {
                        HelpShop hs = new HelpShop();
                        hs.setName(p.getName());
                        hs.setAddress(p.getVicinity());
                        hs.setLat(p.getGeometry().getLocation().getLat());
                        hs.setLng(p.getGeometry().getLocation().getLng());
                        hs.setDistance((int) GoogleMapHelper.distance(latLng.latitude, latLng.longitude, p.getGeometry().getLocation().getLat(), p.getGeometry().getLocation().getLng()));
                        list.add(hs);
                    }
                    rv.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GooglePlaces> call, Throwable t) {
                srl.setRefreshing(false);
                t(t.toString());
            }
        });
    }
}
