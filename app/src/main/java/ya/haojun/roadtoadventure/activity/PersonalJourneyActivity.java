package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.CaloriesHelper;
import ya.haojun.roadtoadventure.helper.GoogleMapHelper;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.LocationRecordModel;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.model.ValueInfo;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;
import ya.haojun.roadtoadventure.sqlite.DAOLocationRecord;

public class PersonalJourneyActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private TextView tv_start_time, tv_end_time, tv_status,
            tv_ride_time, tv_ride_distance, tv_average_speed;
    private EditText et_name, et_content;
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
        et_name = (EditText) findViewById(R.id.et_personal_journey_name);
        et_content = (EditText) findViewById(R.id.et_personal_journey_content);
        tv_start_time = (TextView) findViewById(R.id.tv_personal_journey_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_personal_journey_end_time);
        tv_status = (TextView) findViewById(R.id.tv_personal_journey_status);
        iv_map_picture = (ImageView) findViewById(R.id.iv_personal_journey_map_picture);
        tv_ride_time = (TextView) findViewById(R.id.tv_personal_journey_ride_time);
        tv_ride_distance = (TextView) findViewById(R.id.tv_personal_journey_ride_distance);
        tv_average_speed = (TextView) findViewById(R.id.tv_personal_journey_average_speed);
        findViewById(R.id.tv_personal_journey_detail_info).setOnClickListener(this);
        findViewById(R.id.iv_personal_journey_save).setOnClickListener(this);
        tv_status.setOnClickListener(this);
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
                        et_name.setText(personalJourney.getName());
                        et_content.setText(personalJourney.getContent());
                        tv_start_time.setText(personalJourney.getStartTime().equals("None") ? "" : personalJourney.getStartTime());
                        tv_end_time.setText(personalJourney.getEndTime().equals("None") ? "" : personalJourney.getEndTime());
                        tv_status.setText(getStatusText(personalJourney.getStatus()));
                        calculateRideInfo();
                        showMapPicture();
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

    private void calculateRideInfo() {
        if (personalJourney.getStatus().equals("0")) { // planning
            tv_ride_time.setText("00:00:00");
            tv_ride_distance.setText("0.00公里");
            tv_average_speed.setText("0.00公里/時");
        } else { // riding & finish
            String startTime = personalJourney.getStartTime();
            String endTime = personalJourney.getStatus().equals("1") ? TimeHelper.now() : personalJourney.getEndTime();

            double rideDistanceKM = GoogleMapHelper.distance(new DAOLocationRecord(this).filter(startTime, endTime)) / 1000;
            long secondGap = TimeHelper.toSecond(endTime) - TimeHelper.toSecond(startTime);
            double hour = secondGap / 60 / 60.0;
            double kmPerHour = rideDistanceKM / hour;

            tv_ride_time.setText(TimeHelper.gap(startTime, endTime));
            tv_ride_distance.setText(String.format("%.2f公里", rideDistanceKM));
            tv_average_speed.setText(String.format("%.2f公里/時", kmPerHour));

//            LogHelper.d("km=" + rideDistanceKM);
//            LogHelper.d(CaloriesHelper.calculate(User.getInstance().getWeight(), hour, kmPerHour) + "");
        }
    }

    private void showMapPicture() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Picasso.with(PersonalJourneyActivity.this)
                .load(personalJourney.getPictures().get(0))
                .resize(dm.widthPixels, (int) (dm.density * 200))
                .centerCrop()
                .into(iv_map_picture);
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

    private void endPersonalJourney(String endTime, List<LocationRecordModel> list) {
        PersonalJourney params = new PersonalJourney();
        params.setPersonalJourneyId(personalJourneyId);
        params.setEndTime(endTime);
        params.getRoutes().addAll(list);

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

    private void updatePersonalJourney(final String name, final String content) {
        PersonalJourney params = new PersonalJourney();
        params.setPersonalJourneyId(personalJourneyId);
        params.setName(name);
        params.setContent(content);

        Call<PersonalJourney> call = RoadToAdventureService.service.updatePersonalJourney(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        t(R.string.success);
                        personalJourney.setName(name);
                        personalJourney.setContent(content);
                        setResult(RESULT_OK);
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

    @Override
    public void onClick(View v) {
        hideKeyBoard(v);
        switch (v.getId()) {
            case R.id.iv_personal_journey_save:
                String name = et_name.getText().toString();
                String content = et_content.getText().toString();
                if (name.isEmpty() || content.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }
                updatePersonalJourney(name, content);
                break;
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
                    String startTime = personalJourney.getStartTime();
                    String endTime = TimeHelper.now();
                    List<LocationRecordModel> list = new DAOLocationRecord(this).filter(startTime, endTime);
                    endPersonalJourney(endTime, list);
                }
                break;
        }
    }
}
