package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.GroupMemberRVAdapter;
import ya.haojun.roadtoadventure.model.GroupMember;

public class AddGroupActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private ImageView iv_picture;
    private EditText et_name;
    private RecyclerView rv_member;
    // data
    private ArrayList<GroupMember> list_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        // ui reference
        findViewById(R.id.iv_add_group_done).setOnClickListener(this);
        iv_picture = (ImageView) findViewById(R.id.iv_add_group_picture);
        et_name = (EditText) findViewById(R.id.et_add_group_name);
        rv_member = (RecyclerView) findViewById(R.id.rv_add_group_member);

        // init RecyclerView
        list_member = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list_member.add(new GroupMember("haojun" + i, "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460"));
        }
        rv_member.setLayoutManager(new GridLayoutManager(this, 3));
        rv_member.setAdapter(new GroupMemberRVAdapter(this, list_member));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_group_done:
                break;
        }
    }
}
