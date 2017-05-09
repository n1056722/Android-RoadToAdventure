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
import ya.haojun.roadtoadventure.model.HelpShop;
import ya.haojun.roadtoadventure.retrofit.GoogleMapService;
import ya.haojun.roadtoadventure.sqlite.DAOJourney;


public class HelpFragment extends CommonFragment {

    // type
    public static final int HOSPITAL = 0;
    public static final int HOSTEL = 1;
    public static final int CAR = 2;
    public static final int SHOP = 3;

    // ui
    private RecyclerView rv;
    // extra
    private int type;
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
        rv = (RecyclerView) v.findViewById(R.id.rv_fragment_help);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getArguments().getInt("type");

        // init RecyclerView
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = "";
            switch (type) {
                case HOSPITAL:
                    name = "醫院" + i;
                    break;
                case HOSTEL:
                    name = "旅社" + i;
                    break;
                case CAR:
                    name = "車行" + i;
                    break;
                case SHOP:
                    name = "超商" + i;
                    break;
            }
            list.add(new HelpShop(name, "台北市中正區濟南路一段321號", "02-3322-2777"));
        }
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new HelpRVAdapter(getActivity(), list));
    }
}
