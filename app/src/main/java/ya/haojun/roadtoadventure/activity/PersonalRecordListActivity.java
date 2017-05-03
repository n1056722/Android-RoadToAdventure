package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.PeronalRecordListRVAdapter;

public class PersonalRecordListActivity extends CommonActivity implements View.OnClickListener {


    // ui
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_record_list);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_personal_record_list);
        findViewById(R.id.iv_personal_record_list_add_record).setOnClickListener(this);
        // init RecyclerView
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new PeronalRecordListRVAdapter(this,));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_personal_record_list_add_record:

                break;
        }
    }
}
