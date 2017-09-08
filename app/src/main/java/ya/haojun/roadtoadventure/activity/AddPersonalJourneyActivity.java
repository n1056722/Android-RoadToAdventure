package ya.haojun.roadtoadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.JourneyOpenListAdapter;
import ya.haojun.roadtoadventure.helper.BitmapHelper;
import ya.haojun.roadtoadventure.helper.EncryptHelper;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class AddPersonalJourneyActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int SELECT_JOURNEY = 0;

    // ui
    private Spinner sp_open;
    private TextView tv_starttime;
    private TextView tv_endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_journey);

        // ui reference
        tv_starttime = (TextView) findViewById(R.id.tv_add_personal_journey_starttime);
        tv_endtime = (TextView) findViewById(R.id.tv_add_personal_journey_endtime);
        sp_open = (Spinner) findViewById(R.id.sp_add_personal_journey_open);
        findViewById(R.id.tv_add_personal_journey_direction).setOnClickListener(this);
        findViewById(R.id.tv_add_personal_journey_create).setOnClickListener(this);
        // init Spinner
        tv_starttime.setOnClickListener(this);
        tv_endtime.setOnClickListener(this);

        sp_open.setAdapter(new JourneyOpenListAdapter(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_personal_journey_direction:
                openActivityForResult(SelectDistanceMapActivity.class, SELECT_JOURNEY);
                break;
            case R.id.tv_add_personal_journey_starttime:
            case R.id.tv_add_personal_journey_endtime:
                showDatePickerDialog((TextView) v);
                break;
            case R.id.tv_add_personal_journey_create:
                String name = ((EditText) findViewById(R.id.et_add_personal_journey_name)).getText().toString();
                String content = ((EditText) findViewById(R.id.et_add_personal_journey_content)).getText().toString();
                String starttime = tv_starttime.getText().toString();
                String endtime = tv_endtime.getText().toString();
                if (name.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                if (content.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }
                createPersonalJourney(name, content,starttime ,endtime);
                break;
        }
    }

    private void showDatePickerDialog(final TextView v) {
        final DatePicker dp = new DatePicker(this);
        alertWithView(dp, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String date = dp.getYear() + "-" + ((dp.getMonth() + 1) < 10 ? "0" + (dp.getMonth() + 1) : (dp.getMonth() + 1)) + "-" + (dp.getDayOfMonth() < 10 ? "0" + dp.getDayOfMonth() : dp.getDayOfMonth());
                v.setText(date);
            }
        }, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_JOURNEY:
                    ImageView iv = (ImageView) findViewById(R.id.iv_add_personal_journey_image);
                    iv.setImageBitmap(BitmapHelper.byteArrayToBitmap(data.getByteArrayExtra("image")));
                    break;
            }
        }
    }

    private void createPersonalJourney( String name, String content, String startTime, String endTime) {
        PersonalJourney params = new PersonalJourney();
        params.setUserId(User.getInstance().getUserId());
        params.setName(name);
        params.setContent(content);
        params.setPoints("123");
        params.setStatus("0");
        params.setIsOpen("0");
        params.setStartTime(startTime);
        params.setEndTime(endTime);

        Call<PersonalJourney> call = RoadToAdventureService.service.createPersonalJourney(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        setResult(RESULT_OK);
                        Intent myIntent = new Intent(AddPersonalJourneyActivity.this, PersonalJourneyListActivity.class);
                        AddPersonalJourneyActivity.this.startActivity(myIntent);
                        Toast.makeText(AddPersonalJourneyActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                        finish();
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
