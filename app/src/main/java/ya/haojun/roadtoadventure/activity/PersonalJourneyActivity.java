package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.PersonalJourneyValueInfoRVAdapter;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.model.ValueInfo;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class PersonalJourneyActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private TextView tv_name, tv_content, tv_start_time, tv_end_time, tv_status;
    private ImageView iv_map_picture;
    private RecyclerView rv_value_info;
    // extra
    private int personalJourneyId;
    // data
    private PersonalJourney personalJourney;
    private ArrayList<ValueInfo> list_value_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_journey);

        // ui reference
        tv_name = (TextView) findViewById(R.id.tv_personal_journey_name);
        tv_content = (TextView) findViewById(R.id.tv_personal_journey_content);
        tv_start_time = (TextView) findViewById(R.id.tv_personal_journey_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_personal_journey_end_time);
        tv_status = (TextView) findViewById(R.id.tv_personal_journey_status);
        iv_map_picture = (ImageView) findViewById(R.id.iv_personal_journey_map_picture);
        rv_value_info = (RecyclerView) findViewById(R.id.rv_personal_journey_value_info);
        findViewById(R.id.tv_personal_journey_detail_info).setOnClickListener(this);
        tv_status.setOnClickListener(this);
        iv_map_picture.setOnClickListener(this);
        // extra
        personalJourneyId = getIntent().getExtras().getInt("personalJourneyId");
        // init
        list_value_info = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });
        rv_value_info.setLayoutManager(layoutManager);
        rv_value_info.setAdapter(new PersonalJourneyValueInfoRVAdapter(this, list_value_info));
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

    private void setValueInfo() {
        list_value_info.clear();
        list_value_info.add(new ValueInfo("已騎乘時間", "00:00:00"));
        list_value_info.add(new ValueInfo("已騎乘距離", "1.23公里"));
        list_value_info.add(new ValueInfo("平均時速", "0.00公里/時"));
        rv_value_info.getAdapter().notifyDataSetChanged();
    }

    private void startPersonalJourney() {
        PersonalJourney params = new PersonalJourney();
        params.setPersonalJourneyId(personalJourneyId);
        params.setStartTime(TimeHelper.now());

        Call<PersonalJourney> call = RoadToAdventureService.service.startPersonalJourney(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        getPersonalJourney();
                    } else {
                        t(R.string.fail);
                        finish();
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

    private void endPersonalJourney() {
        PersonalJourney params = new PersonalJourney();
        params.setPersonalJourneyId(personalJourneyId);
        params.setEndTime(TimeHelper.now());

        Call<PersonalJourney> call = RoadToAdventureService.service.endPersonalJourney(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        getPersonalJourney();
                    } else {
                        t(R.string.fail);
                        finish();
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

    private String getStatusText(String status) {
        switch (Integer.valueOf(status)) {
            case 0:
                return getString(R.string.start);
            case 1:
                return getString(R.string.stop);
            default:
                tv_status.setVisibility(View.GONE);
                return "";
        }
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
            case R.id.tv_personal_journey_status:
                if (personalJourney.getStatus().equals("0")) {
                    startPersonalJourney();
                } else if (personalJourney.getStatus().equals("1")) {
                    endPersonalJourney();
                }
                break;
        }
    }
}
