package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class PersonalJourneyActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private TextView tv_name, tv_content, tv_start_time, tv_end_time;
    private ImageView iv_map_picture;
    // extra
    private int personalJourneyId;
    // data
    private PersonalJourney personalJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_journey);

        // ui reference
        tv_name = (TextView) findViewById(R.id.tv_personal_journey_name);
        tv_content = (TextView) findViewById(R.id.tv_personal_journey_content);
        tv_start_time = (TextView) findViewById(R.id.tv_personal_journey_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_personal_journey_end_time);
        iv_map_picture = (ImageView) findViewById(R.id.iv_personal_journey_map_picture);
        findViewById(R.id.tv_personal_journey_detail_info).setOnClickListener(this);
        iv_map_picture.setOnClickListener(this);
        // extra
        personalJourneyId = getIntent().getExtras().getInt("personalJourneyId");
        // init
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
                        tv_name.setText(personalJourney.getName());
                        tv_content.setText(personalJourney.getContent());
                        tv_start_time.setText(personalJourney.getStartTime());
                        tv_end_time.setText(personalJourney.getEndTime());
                        LogHelper.d(personalJourney.toString());
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
            case R.id.tv_personal_journey_detail_info:
                View ll_detail_info = findViewById(R.id.ll_personal_journey_detail_info);
                ll_detail_info.setVisibility(ll_detail_info.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.iv_personal_journey_map_picture:
                Bundle b = new Bundle();
                b.putString("picturePath", personalJourney.getPictures().get(0));
                openActivity(PictureActivity.class, b);
                break;
        }
    }
}
