package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.GroupMemberRVAdapter;
import ya.haojun.roadtoadventure.model.GroupMember;

public class GroupMemberActivity extends CommonActivity {

    // ui
    private RecyclerView rv_member;
    // data
    private ArrayList<GroupMember> list_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);

        // ui reference
        rv_member = (RecyclerView) findViewById(R.id.rv_group_member);

        // init RecyclerView
        list_member = new ArrayList<>();
        rv_member.setLayoutManager(new GridLayoutManager(this, 3));
        rv_member.setAdapter(new GroupMemberRVAdapter(this, list_member));
    }
}
