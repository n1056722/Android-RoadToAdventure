package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ya.haojun.roadtoadventure.R;

public class PersonalRecordListActivity extends CommonActivity {


    // ui
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_record_list);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_personal_record_list);

        // init RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter();
    }
}
