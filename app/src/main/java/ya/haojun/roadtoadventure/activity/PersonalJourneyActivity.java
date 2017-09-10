package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class PersonalJourneyActivity extends CommonActivity {

    // ui
    private TextView tv_name, tv_content, tv_start_time, tv_end_time;
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
}
