package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.InviteMemberRVAdapter;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class InviteMemberActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    // data
    private ArrayList<Friend> list_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_member);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_invite_member);
        findViewById(R.id.iv_invite_member_done).setOnClickListener(this);

        // init
        list_friend = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new InviteMemberRVAdapter(this, list_friend));
        getFriendsList();
    }

    private void getFriendsList() {
        Friend params = new Friend();
        params.setUserId(User.getInstance().getUserId());

        Call<Friend> call = RoadToAdventureService.service.getFriendsList(params);
        showLoadingDialog();
        call.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(Call<Friend> call, Response<Friend> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Friend result = response.body();
                    if (result.isSuccess()) {
                        list_friend.clear();
                        list_friend.addAll(result.getFriends());
                        rv.getAdapter().notifyDataSetChanged();
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<Friend> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_invite_member_done:
                ArrayList<Integer> list_selected = ((InviteMemberRVAdapter)rv.getAdapter()).getSelected();
                ArrayList<Friend> list_member = new ArrayList<>();
                for (Integer i : list_selected){
                    list_member.add(list_friend.get(i));
                }
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("members", list_member);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
