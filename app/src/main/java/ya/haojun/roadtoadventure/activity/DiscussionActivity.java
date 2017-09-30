package ya.haojun.roadtoadventure.activity;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.DiscussionCommentRVAdapter;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.PersonalJourneyComment;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class DiscussionActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private SwipeRefreshLayout srl;
    private NestedScrollView nsv;
    private ImageView iv_user_picture;
    private TextView tv_user_name, tv_modify_date;
    private TextView tv_journey_name, tv_journey_content;
    private RecyclerView rv_comments;
    private FloatingActionButton fab_create_comment;
    // extra
    private int personalJourneyId;
    // data
    private PersonalJourney personalJourney;
    private ArrayList<PersonalJourneyComment> list_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        setUpToolbar("騎乘攻略");

        // ui reference
        srl = (SwipeRefreshLayout) findViewById(R.id.srl_discussion);
        nsv = (NestedScrollView) findViewById(R.id.nsv_discussion);
        iv_user_picture = (ImageView) findViewById(R.id.iv_discussion_user_picture);
        tv_user_name = (TextView) findViewById(R.id.tv_discussion_user_name);
        tv_modify_date = (TextView) findViewById(R.id.tv_discussion_modify_date);
        tv_journey_name = (TextView) findViewById(R.id.tv_discussion_journey_name);
        tv_journey_content = (TextView) findViewById(R.id.tv_discussion_journey_content);
        rv_comments = (RecyclerView) findViewById(R.id.rv_discussion_comments);
        fab_create_comment = (FloatingActionButton) findViewById(R.id.fab_discussion_create_comment);
        fab_create_comment.setOnClickListener(this);

        // init
        personalJourneyId = getIntent().getExtras().getInt("personalJourneyId");

        // init
        list_comment = new ArrayList<>();
        rv_comments.setLayoutManager(new LinearLayoutManager(this));
        rv_comments.setAdapter(new DiscussionCommentRVAdapter(this, list_comment));
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    fab_create_comment.hide();
                } else if (scrollY < oldScrollY) {
                    fab_create_comment.show();
                }
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                getPersonalJourney(false);
            }
        });
        srl.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        getPersonalJourney(false);
    }

    private void getPersonalJourney(final boolean scrollToBottom) {
        PersonalJourney params = new PersonalJourney();
        params.setPersonalJourneyId(personalJourneyId);

        Call<PersonalJourney> call = RoadToAdventureService.service.getPersonalJourney(params);
        srl.setRefreshing(true);
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                srl.setRefreshing(false);
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        personalJourney = result;
                        int w = (int) (getResources().getDisplayMetrics().density * 60);
                        Picasso.with(DiscussionActivity.this)
                                .load(personalJourney.getUserPicture())
                                .resize(w, w)
                                .centerCrop()
                                .into(iv_user_picture);
                        tv_user_name.setText(personalJourney.getUserName());
                        tv_modify_date.setText(personalJourney.getModifyDate());
                        tv_journey_name.setText(personalJourney.getName());
                        tv_journey_content.setText(personalJourney.getContent());

                        // comments
                        list_comment.clear();
                        list_comment.addAll(personalJourney.getComments());
                        rv_comments.getAdapter().notifyDataSetChanged();

                        if (scrollToBottom) {
                            LogHelper.d("A");
                            nsv.fullScroll(View.FOCUS_DOWN);
                        }
                    } else {
                        t(R.string.fail);
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonalJourney> call, Throwable t) {
                srl.setRefreshing(false);
                t(t.toString());
            }
        });
    }

    private void createPersonalJourneyComment(String comment) {
        PersonalJourneyComment params = new PersonalJourneyComment();
        params.setUserId(User.getInstance().getUserId());
        params.setComment(comment);
        params.setPersonalJourneyId(personalJourneyId);

        Call<PersonalJourneyComment> call = RoadToAdventureService.service.createPersonalJourneyComment(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourneyComment>() {
            @Override
            public void onResponse(Call<PersonalJourneyComment> call, Response<PersonalJourneyComment> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourneyComment result = response.body();
                    if (result.isSuccess()) {
                        getPersonalJourney(true);
                    } else {
                        t(R.string.fail);
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonalJourneyComment> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    private void showCommentDialog() {
        final View v = LayoutInflater.from(this).inflate(R.layout.dialog_discussion_comment, null);
        alertWithView(v, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comment = ((EditText) v.findViewById(R.id.et_dialog_discussion_comment_comment)).getText().toString();
                if (comment.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }
                createPersonalJourneyComment(comment);
            }
        }, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_discussion_create_comment:
                showCommentDialog();
                break;
        }
    }
}
