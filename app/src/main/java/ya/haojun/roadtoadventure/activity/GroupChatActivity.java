package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.ChatRVAdapter;
import ya.haojun.roadtoadventure.adapter.GroupChatRVAdapter;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.FriendChat;
import ya.haojun.roadtoadventure.model.Group;
import ya.haojun.roadtoadventure.model.GroupChat;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;
import ya.haojun.roadtoadventure.sqlite.DAOGroupChat;

public class GroupChatActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    private EditText et_input;
    // extra
    private Group group;
    // data
    private ArrayList<GroupChat> list_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_group_chat);
        et_input = (EditText) findViewById(R.id.et_group_chat_input);
        findViewById(R.id.iv_group_chat_send).setOnClickListener(this);

        // extra
        group = getIntent().getExtras().getParcelable("group");

        // init
        list_chat = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new GroupChatRVAdapter(this, list_chat));

        getLocalGroupChat();
        getGroupChat();
    }

    private void getLocalGroupChat() {
        ArrayList<GroupChat> list = new DAOGroupChat(this).filter(group.getGroupId());
        list_chat.clear();
        list_chat.addAll(list);
        rv.getAdapter().notifyDataSetChanged();
        rv.scrollToPosition(list_chat.size() - 1);
    }

    private void getGroupChat() {
        GroupChat params = new GroupChat();
        params.setGroupId(group.getGroupId());

        Call<GroupChat> call = RoadToAdventureService.service.getGroupChatList(params);
        showLoadingDialog();
        call.enqueue(new Callback<GroupChat>() {
            @Override
            public void onResponse(Call<GroupChat> call, Response<GroupChat> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    GroupChat result = response.body();
                    if (result.isSuccess()) {
                        ArrayList<GroupChat> list = result.getGroupChats();
                        if (!list.isEmpty()) {
                            DAOGroupChat daoGroupChat = new DAOGroupChat(GroupChatActivity.this);
                            for (GroupChat gc : list) {
                                if (daoGroupChat.get(gc.getGroupChatId()) == null) {
                                    daoGroupChat.insert(gc);
                                }
                            }
                            getLocalGroupChat();
                        }
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupChat> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    private void createGroupChat(String content) {
        GroupChat params = new GroupChat();
        params.setGroupId(group.getGroupId());
        params.setUserId(User.getInstance().getUserId());
        params.setContent(content);
        params.setLastChatId(new DAOGroupChat(this).getLastId());

        Call<GroupChat> call = RoadToAdventureService.service.createGroupChat(params);
        showLoadingDialog();
        call.enqueue(new Callback<GroupChat>() {
            @Override
            public void onResponse(Call<GroupChat> call, Response<GroupChat> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    GroupChat result = response.body();
                    if (result.isSuccess()) {
                        et_input.setText("");
                        DAOGroupChat daoGroupChat = new DAOGroupChat(GroupChatActivity.this);
                        for (GroupChat gc : result.getGroupChats()) {
                            daoGroupChat.insert(gc);
                        }
                        getLocalGroupChat();
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupChat> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_group_chat_send:
                String input = et_input.getText().toString();
                if (input.isEmpty()) return;
                createGroupChat(input);
                break;
        }
    }
}
