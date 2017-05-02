package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.ChatRVAdapter;
import ya.haojun.roadtoadventure.model.Chat;

public class FriendChatActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    private EditText et_input;
    // data
    private ArrayList<Chat> list_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_friend_chat);
        et_input = (EditText) findViewById(R.id.et_friend_chat_input);
        findViewById(R.id.iv_friend_chat_send).setOnClickListener(this);

        // init RecyclerView
        list_chat = new ArrayList<>();
        list_chat.add(new Chat("hj", "haojun", "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460", "HI", "2017-05-01 00:00:00"));
        list_chat.add(new Chat("hj2", "haojun2", "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460", "HI2", "2017-05-02 00:00:00"));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ChatRVAdapter(this, list_chat));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_friend_chat_send:
                break;
        }
    }
}
