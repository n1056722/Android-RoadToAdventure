package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.ChatRVAdapter;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.FriendChat;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.GroupChat;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;
import ya.haojun.roadtoadventure.sqlite.DAOFriendChat;
import ya.haojun.roadtoadventure.sqlite.DAOGroupChat;

public class FriendChatActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    private EditText et_input;
    // data
    private Friend friend;
    private ArrayList<FriendChat> list_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_friend_chat);
        et_input = (EditText) findViewById(R.id.et_friend_chat_input);
        findViewById(R.id.iv_friend_chat_send).setOnClickListener(this);
        friend = getIntent().getExtras().getParcelable("friend");
        // init RecyclerView
        list_chat = new ArrayList<>();
        //list_chat.add(new FriendChat("hj", "haojun", "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460", "HI", "2017-05-01 00:00:00"));
        //list_chat.add(new FriendChat("hj2", "haojun2", "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460", "HI2", "2017-05-02 00:00:00"));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ChatRVAdapter(this, list_chat));

        getLocalFriendChat();
        getFriendChat();
    }

    private void getLocalFriendChat() {
        ArrayList<FriendChat> list = new DAOFriendChat(this).filter(User.getInstance().getUserId(), friend.getUserId());
        list_chat.clear();
        list_chat.addAll(list);
        rv.getAdapter().notifyDataSetChanged();
        rv.scrollToPosition(list_chat.size() - 1);
    }

    private void getFriendChat() {
        FriendChat params = new FriendChat();
        params.setUserID(User.getInstance().getUserId());
        params.setFriendID(friend.getUserId());
        params.setLastChatId(!list_chat.isEmpty() ? list_chat.get(list_chat.size() - 1).getChatID() : 0);

        Call<FriendChat> call = RoadToAdventureService.service.getFriendChatList(params);
        showLoadingDialog();
        call.enqueue(new Callback<FriendChat>() {
            @Override
            public void onResponse(Call<FriendChat> call, Response<FriendChat> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    FriendChat result = response.body();
                    if (result.isSuccess()) {
                        ArrayList<FriendChat> list = result.getChats();
                        if (!list.isEmpty()) {
                            DAOFriendChat daoFriendChat = new DAOFriendChat(FriendChatActivity.this);
                            for (FriendChat fc : list) {
                                if (daoFriendChat.get(fc.getChatID()) == null) {
                                    daoFriendChat.insert(fc);
                                }
                            }
                            getLocalFriendChat();
                        }
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendChat> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }
    private void createFriendChat(String content) {
        FriendChat params = new FriendChat();
        params.setUserID(User.getInstance().getUserId());
        params.setFriendID(friend.getUserId());
        params.setContent(content);
        params.setLastChatId(!list_chat.isEmpty() ? list_chat.get(list_chat.size() - 1).getChatID() : 0);

        Call<FriendChat> call = RoadToAdventureService.service.createFriendChat(params);
        showLoadingDialog();
        call.enqueue(new Callback<FriendChat>() {
            @Override
            public void onResponse(Call<FriendChat> call, Response<FriendChat> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    FriendChat result = response.body();
                    if (result.isSuccess()) {
                        et_input.setText("");
                        DAOFriendChat daoFriendChat = new DAOFriendChat(FriendChatActivity.this);
                        for (FriendChat fc : result.getChats()) {
                            daoFriendChat.insert(fc);
                        }
                        getLocalFriendChat();
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendChat> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_friend_chat_send:
                break;
        }
    }
}
