package ya.haojun.roadtoadventure.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class DiscussionActivity extends CommonActivity implements View.OnClickListener {

    // ui
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
        rv_comments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogHelper.d(dy + "");
            }
        });
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

        getPersonalJourney();
    }

    private void getPersonalJourney() {
        PersonalJourney params = new PersonalJourney();
        params.setPersonalJourneyId(personalJourneyId);

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
            case R.id.fab_discussion_create_comment:

                break;
        }
    }
}
