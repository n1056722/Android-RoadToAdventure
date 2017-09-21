package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
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
import ya.haojun.roadtoadventure.model.Chat;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

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
    private void getPersonalJourney(String friendId) {
        Friend params = new Friend();
        params.setUserId(User.getInstance().getUserId());
        params.setFriendId(friendId);


        Call<PersonalJourney> call = RoadToAdventureService.service.getPersonalJourney(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        personalJourney = result;
                        tv_name.setText(personalJourney.getName());
                        tv_content.setText(personalJourney.getContent());
                        tv_start_time.setText(personalJourney.getStartTime());
                        tv_end_time.setText(personalJourney.getEndTime());
                        tv_status.setText(getStatusText(personalJourney.getStatus()));
                        rv_value_info.setVisibility(personalJourney.getStatus().equals("0") ? View.GONE : View.VISIBLE);
                        setValueInfo();
                        DisplayMetrics dm = getResources().getDisplayMetrics();
                        Picasso.with(PersonalJourneyActivity.this)
                                .load(personalJourney.getPictures().get(0))
                                .resize(dm.widthPixels, (int) (dm.density * 200))
                                .centerCrop()
                                .into(iv_map_picture);
                    } else {
                        t(R.string.fail);
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonalJourney> call, Throwable t) {
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
