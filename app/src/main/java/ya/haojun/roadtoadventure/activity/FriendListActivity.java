package ya.haojun.roadtoadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.FriendListRVAdapter;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class FriendListActivity extends CommonActivity implements View.OnClickListener {

    public static final int REQUEST_SEARCH = 0;
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

        // init
        list_friend = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new FriendListRVAdapter(this, list_friend));
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
                    list_friend.clear();
                    if (result.isSuccess()) {
                        list_friend.addAll(result.getFriends());
                    } else {
                        t(R.string.empty);
                    }
                    rv.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Friend> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }
    private void deleteFriend(String friendId) {
        Friend params = new Friend();
        params.setUserId(User.getInstance().getUserId());
        params.setFriendId(friendId);

        Call<Friend> call = RoadToAdventureService.service.deleteFriend(params);
        showLoadingDialog();
        call.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(Call<Friend> call, Response<Friend> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Friend result = response.body();
                    if (result.isSuccess()) {
                       getFriendsList();
                    } else {
                        t(R.string.fail);
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

    public void deleteFriendDialog(final String friendId) {
        new AlertDialog.Builder(FriendListActivity.this)
                .setTitle("刪除好友")
                .setMessage("你確定要刪除此好友嗎")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFriend(friendId);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_friend_list_search:
                openActivityForResult(SearchFriendActivity.class, REQUEST_SEARCH);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)return;
        switch (requestCode){
            case REQUEST_SEARCH:
                getFriendsList();
                break;
        }
    }
}

