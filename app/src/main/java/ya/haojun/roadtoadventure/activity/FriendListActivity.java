package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.FriendRVAdapter;
import ya.haojun.roadtoadventure.model.Friend;

public class FriendListActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    // data
    private ArrayList<Friend> list_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_friend_list);
        findViewById(R.id.iv_friend_list_search).setOnClickListener(this);

        // init RecyclerView
        list_friend = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list_friend.add(new Friend("hj" + i, "haojun" + i, "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460"));
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new FriendRVAdapter(this, list_friend));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_friend_list_search:
                openActivity(SearchFriendActivity.class);
                break;
        }
    }
}
