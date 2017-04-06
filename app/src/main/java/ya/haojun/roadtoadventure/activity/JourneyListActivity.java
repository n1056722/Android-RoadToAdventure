package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.JourneyListAdapter;
import ya.haojun.roadtoadventure.model.JourneyModel;
import ya.haojun.roadtoadventure.model.LocationRecordModel;
import ya.haojun.roadtoadventure.sqlite.DAOJourney;
import ya.haojun.roadtoadventure.sqlite.DAOLocationRecord;

public class JourneyListActivity extends CommonActivity {

    // ui
    private ListView lv;
    // data
    private ArrayList<JourneyModel> list_journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);
        // ui reference
        lv = (ListView) findViewById(R.id.lv_journey_list);
        // init ListView
        list_journey = new ArrayList<>();
        lv.setAdapter(new JourneyListAdapter(this, list_journey));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JourneyModel jm = list_journey.get(position);
                StringBuilder sb = new StringBuilder();
                sb.append("StartTime : ").append(jm.getStartTime()).append("\n");
                sb.append("StopTime : ").append(jm.getStopTime()).append("\n\n");
                List<LocationRecordModel> records = new DAOLocationRecord(JourneyListActivity.this).filter(jm.getStartTime(), jm.getStopTime());
                for (LocationRecordModel r : records) {
                    sb.append(r.getLatitude()).append(" ").append(r.getLongitude()).append("\n");
                }

                t(sb.toString());
            }
        });
        // load
        loadJourneyList();
    }

    private void updateAdapter() {
        ((JourneyListAdapter) lv.getAdapter()).notifyDataSetChanged();
    }

    private void loadJourneyList() {
        DAOJourney daoJourney = new DAOJourney(this);
        list_journey.clear();
        list_journey.addAll(daoJourney.getAll());
        updateAdapter();
    }
}
