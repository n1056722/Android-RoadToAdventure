package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.SearchFriendRVAdapter;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class SearchFriendActivity extends CommonActivity implements View.OnClickListener {
    private RecyclerView rv;
    private ArrayList<Friend> list_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        rv = (RecyclerView) findViewById(R.id.rv_friend_search);
        // ui reference
        findViewById(R.id.tv_search_friend_search).setOnClickListener(this);
        list_search = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new SearchFriendRVAdapter(this, list_search));
    }

    private void searchFriends(String input) {
        Friend params = new Friend();
        params.setUserName(input);

        Call<Friend> call = RoadToAdventureService.service.searchFriends(params);
        showLoadingDialog();
        call.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(Call<Friend> call, Response<Friend> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Friend result = response.body();
                    if (result.isSuccess()) {
                        list_search.clear();
                        for (Friend f : result.getFriends()) {
                            if (!f.getUserId().equals(User.getInstance().getUserId())) {
                                list_search.add(f);
                            }
                        }
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

    public void createFriend(String friendId) {
        Toast.makeText(this, User.getInstance().getUserId() + " " + friendId, Toast.LENGTH_SHORT).show();
        Friend params = new Friend();
        params.setUserId(User.getInstance().getUserId());
        params.setFriendId(friendId);

        Call<Friend> call = RoadToAdventureService.service.createFriend(params);
        showLoadingDialog();
        call.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(Call<Friend> call, Response<Friend> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Friend result = response.body();
                    if (result.isSuccess()) {
                        t(R.string.success);
                        setResult(RESULT_OK);
                        finish();
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
        switch (v.getId()) {
            case R.id.tv_search_friend_search:
                String input = ((EditText) findViewById(R.id.et_search_friend_input)).getText().toString();
                if (input.isEmpty()) {
                    t("請輸入");
                    return;
                }
                searchFriends(input);
                break;
        }
    }
}
