package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.GroupMemberRVAdapter;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.Group;
import ya.haojun.roadtoadventure.model.GroupMember;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class GroupMemberActivity extends CommonActivity {

    // request
    public static final int REQUEST_INVITE_MEMBER = 0;
    // ui
    private RecyclerView rv_member;
    // extra
    private Group group;
    // data
    private ArrayList<GroupMember> list_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);

        // ui reference
        rv_member = (RecyclerView) findViewById(R.id.rv_group_member);

        // extra
        group = getIntent().getExtras().getParcelable("group");

        // init
        list_member = new ArrayList<>();
        rv_member.setLayoutManager(new GridLayoutManager(this, 3));
        rv_member.setAdapter(new GroupMemberRVAdapter(this, list_member, GroupMemberRVAdapter.GROUP_MEMBER));

        getGroupMember();
    }

    private void getGroupMember() {
        list_member.clear();
        list_member.addAll(group.getMembers());
        rv_member.getAdapter().notifyDataSetChanged();
    }

    private void createGroupMember(final ArrayList<GroupMember> members) {
        Group params = new Group();
        params.setGroupId(group.getGroupId());
        params.setUserId(User.getInstance().getUserId());
        params.getMembers().addAll(members);

        Call<GroupMember> call = RoadToAdventureService.service.createGroupMember(params);
        showLoadingDialog();
        call.enqueue(new Callback<GroupMember>() {
            @Override
            public void onResponse(Call<GroupMember> call, Response<GroupMember> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    GroupMember result = response.body();
                    if (result.isSuccess()) {
                        // update view
                        list_member.addAll(members);
                        rv_member.getAdapter().notifyDataSetChanged();
                        t(R.string.success);

                        // reset Group
                        group.getMembers().clear();
                        group.getMembers().addAll(list_member);

                        Intent intent = new Intent();
                        intent.putExtra("group", group);
                        setResult(RESULT_OK, intent);
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupMember> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    public void displayMemberInfo(GroupMember member) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_INVITE_MEMBER:
                ArrayList<Friend> list = data.getParcelableArrayListExtra("members");
                ArrayList<GroupMember> members = new ArrayList<>();
                for (Friend f : list) {
                    members.add(new GroupMember(f.getUserId(), f.getUserName(), f.getUserPicture()));
                }
                createGroupMember(members);
                break;
        }
    }
}
