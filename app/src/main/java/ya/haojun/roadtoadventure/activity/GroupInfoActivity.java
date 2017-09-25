package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.Group;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class GroupInfoActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int REQUEST_MEMBER = 0;
    // ui
    private ImageView iv_group_picture;
    private TextView tv_group_name;
    // extra
    private int groupId;
    // data
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        // ui reference
        iv_group_picture = (ImageView) findViewById(R.id.iv_group_info_picture);
        tv_group_name = (TextView) findViewById(R.id.tv_group_info_name);
        findViewById(R.id.cv_group_info_member).setOnClickListener(this);
        findViewById(R.id.cv_group_info_chat).setOnClickListener(this);
        // extra
        groupId = getIntent().getExtras().getInt("groupId");
        // init

        getGroup();
    }

    private void getGroup() {
        Group params = new Group();
        params.setGroupId(groupId);

        Call<Group> call = RoadToAdventureService.service.getGroup(params);
        showLoadingDialog();
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Group result = response.body();
                    if (result.isSuccess()) {
                        group = result;
                        displayGroupPicture();
                        tv_group_name.setText(group.getName());
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

    private void displayGroupPicture() {
        int w = getResources().getDisplayMetrics().widthPixels;
        int h = (int) (getResources().getDisplayMetrics().density * 192);
        Picasso.with(this)
                .load(group.getPicturePath())
                .resize(w, h)
                .centerCrop()
                .into(iv_group_picture);
    }

    @Override
    public void onClick(View v) {
        Bundle b = new Bundle();
        b.putParcelable("group", group);
        switch (v.getId()) {
            case R.id.cv_group_info_member:
                openActivityForResult(GroupMemberActivity.class, REQUEST_MEMBER, b);
                break;
            case R.id.cv_group_info_chat:
                openActivity(GroupChatActivity.class, b);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_MEMBER:
                group = data.getParcelableExtra("group");
                break;
        }
    }
}
