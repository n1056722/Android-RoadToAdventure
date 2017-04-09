package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
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
                Bundle b = new Bundle();
                b.putParcelable("journey", list_journey.get(position));
                openActivity(JourneyRecordActivity.class, b);
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
