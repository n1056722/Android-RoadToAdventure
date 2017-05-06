package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.GroupListRVAdapter;
import ya.haojun.roadtoadventure.model.Group;

public class GroupListActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    // data
    private ArrayList<Group> list_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        // ui reference
        findViewById(R.id.iv_group_list_add_group).setOnClickListener(this);
        rv = (RecyclerView) findViewById(R.id.rv_group_list);

        // init RecyclerView
        list_group = new ArrayList<>();
        for(int i=0;i<10;i++){
            list_group.add(new Group("Group"+i,"http://barkpost-assets.s3.amazonaws.com/wp-content/uploads/2013/11/muchdoge-700x393.jpg"));
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new GroupListRVAdapter(this,list_group));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_group_list_add_group:
                break;
        }
    }
}
