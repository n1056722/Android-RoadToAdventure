package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.GroupListRVAdapter;
import ya.haojun.roadtoadventure.model.Group;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class GroupListActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int REQUEST_ADD = 0;
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
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new GroupListRVAdapter(this, list_group));

        getGroupList();
    }

    private void getGroupList() {
        User params = new User();
        params.setUserId(User.getInstance().getUserId());

        Call<Group> call = RoadToAdventureService.service.getGroupList(params);
        showLoadingDialog();
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Group result = response.body();
                    if (result.isSuccess()) {
                        list_group.clear();
                        list_group.addAll(result.getGroups());
                        rv.getAdapter().notifyDataSetChanged();
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_group_list_add_group:
                openActivityForResult(AddGroupActivity.class, REQUEST_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)return;
        switch (requestCode){
            case REQUEST_ADD:
                getGroupList();
                break;
        }
    }
}
