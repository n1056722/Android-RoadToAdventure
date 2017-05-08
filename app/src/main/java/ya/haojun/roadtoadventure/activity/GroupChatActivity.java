package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.ChatRVAdapter;
import ya.haojun.roadtoadventure.model.Chat;

public class GroupChatActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    private EditText et_input;
    // data
    private ArrayList<Chat> list_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_group_chat);
        et_input = (EditText) findViewById(R.id.et_group_chat_input);
        findViewById(R.id.iv_group_chat_send).setOnClickListener(this);

        // init RecyclerView
        list_chat = new ArrayList<>();
        list_chat.add(new Chat("hj", "haojun", "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460", "HI", "2017-05-01 00:00:00"));
        for (int i = 0; i < 10; i++) {
            list_chat.add(new Chat("hj" + i, "haojun" + i, "https://avatars1.githubusercontent.com/u/15250400?v=3&s=460", "HI" + i, "2017-05-02 00:00:00"));
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ChatRVAdapter(this, list_chat));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_group_chat_send:
                break;
        }
    }
}
