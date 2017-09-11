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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.JourneyOpenListAdapter;
import ya.haojun.roadtoadventure.helper.BitmapHelper;
import ya.haojun.roadtoadventure.helper.EncryptHelper;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.Picture;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class AddPersonalJourneyActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int SELECT_JOURNEY = 0;
    // ui
    private ImageView iv_map_picture;
    private Spinner sp_open;
    private TextView tv_start_date, tv_start_time;
    private TextView tv_end_date, tv_end_time;
    // select distance extra
    private File file_map_picture;
    private String points;
    // data
    private PersonalJourney personalJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_journey);

        // ui reference
        iv_map_picture = (ImageView) findViewById(R.id.iv_add_personal_journey_map_picture);
//        tv_start_date = (TextView) findViewById(R.id.tv_add_personal_journey_start_date);
//        tv_start_time = (TextView) findViewById(R.id.tv_add_personal_journey_start_time);
//        tv_end_date = (TextView) findViewById(R.id.tv_add_personal_journey_end_date);
//        tv_end_time = (TextView) findViewById(R.id.tv_add_personal_journey_end_time);
        sp_open = (Spinner) findViewById(R.id.sp_add_personal_journey_open);
        findViewById(R.id.iv_add_personal_journey_map_picture).setOnClickListener(this);
        findViewById(R.id.tv_add_personal_journey_direction).setOnClickListener(this);
        findViewById(R.id.iv_add_personal_journey_done).setOnClickListener(this);
//        tv_start_date.setOnClickListener(this);
//        tv_start_time.setOnClickListener(this);
//        tv_end_date.setOnClickListener(this);
//        tv_end_time.setOnClickListener(this);
        // init
        sp_open.setAdapter(new JourneyOpenListAdapter(this));

        personalJourney = new PersonalJourney();
    }

    private void createPersonalJourney(String name, String content, String picturePath) {
        PersonalJourney params = new PersonalJourney();
        params.setUserId(User.getInstance().getUserId());
        params.setName(name);
        params.setContent(content);
        params.setPoints(points == null ? "" : points);
        params.setStatus("0");
        params.setIsOpen(sp_open.getSelectedItemPosition() == 0 ? "1" : "0");
        params.getPictures().add(picturePath);

        Call<PersonalJourney> call = RoadToAdventureService.service.createPersonalJourney(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        t(R.string.success);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        LogHelper.d(result.getMessage());
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

    private void createPicture(final String name, final String content) {
        if (file_map_picture == null) {
            t("未選擇路線");
            return;
        }
        MultipartBody.Part fileName = MultipartBody.Part.createFormData("fileName", UUID.randomUUID().toString());
        MultipartBody.Part subFileName = MultipartBody.Part.createFormData("subFileName", "jpg");
        MultipartBody.Part type = MultipartBody.Part.createFormData("type", "1");
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file_map_picture.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file_map_picture));

        Call<Picture> call = RoadToAdventureService.service.createPicture(fileName, subFileName, type, body);
        showLoadingDialog();
        call.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Picture result = response.body();
                    if (result.isSuccess()) {
                        createPersonalJourney(name, content, result.getPicturePath());
                    } else {
                        t(R.string.fail);
                    }
                }
            }

            @Override
            public void onFailure(Call<Picture> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    private void showDatePickerDialog(final TextView tv) {
        final DatePicker dp = new DatePicker(this);
        alertWithView(dp, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = dp.getYear();
                int month = dp.getMonth() + 1;
                int date = dp.getDayOfMonth();
                String dayOfWeek = getDayOfWeek(year, month, date);
                tv.setText(String.format("%d-%s-%s , %s", year, (month < 10 ? "0" : "") + month, (date < 10 ? "0" : "") + date, dayOfWeek));
            }
        }, null);
    }

    private String getDayOfWeek(int year, int month, int date) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, date);
        int dow = c.get(Calendar.DAY_OF_WEEK);
        dow = dow - 1 == 0 ? 7 : dow - 1;

        return new String[]{"", "週一", "週二", "週三", "週四", "週五", "週六", "週日"}[dow];
    }

    private void showTimePickerDialog(final TextView tv) {
        final String[] arr_hour = getTimeArray(24);
        final String[] arr_minute = getTimeArray(59);
        final View v = LayoutInflater.from(this).inflate(R.layout.dialog_time_picker, null);
        final NumberPicker np_hour = (NumberPicker) v.findViewById(R.id.np_dialog_time_picker_hour);
        final NumberPicker np_minute = (NumberPicker) v.findViewById(R.id.np_dialog_time_picker_minute);
        np_hour.setMinValue(0);
        np_hour.setMaxValue(24);
        np_hour.setDisplayedValues(arr_hour);
        np_minute.setMinValue(0);
        np_minute.setMaxValue(59);
        np_minute.setDisplayedValues(arr_minute);
        alertWithView(v, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour = np_hour.getValue();
                int minute = np_minute.getValue();
                tv.setText(String.format("%s:%s %s", arr_hour[np_hour.getValue()], arr_minute[np_minute.getValue()], hour < 12 ? "上午" : "下午"));
            }
        }, null);
    }

    private String[] getTimeArray(int end) {
        String[] arr = new String[end + 1];
        for (int i = 0; i <= end; i++) {
            arr[i] = (i < 10 ? "0" : "") + i;
        }
        return arr;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_personal_journey_map_picture:
                openActivityForResult(SelectDistanceMapActivity.class, SELECT_JOURNEY);
                break;
//            case R.id.tv_add_personal_journey_start_date:
//            case R.id.tv_add_personal_journey_end_date:
//                showDatePickerDialog((TextView) v);
//                break;
//            case R.id.tv_add_personal_journey_start_time:
//            case R.id.tv_add_personal_journey_end_time:
//                showTimePickerDialog((TextView) v);
//                break;
            case R.id.iv_add_personal_journey_done:
                String name = ((EditText) findViewById(R.id.et_add_personal_journey_name)).getText().toString();
                String content = ((EditText) findViewById(R.id.et_add_personal_journey_content)).getText().toString();
//                String startTime = tv_start_date.getText().toString().substring(0, 10) + " " + tv_start_time.getText().toString().substring(0, 5);
//                String endTime = tv_end_date.getText().toString().substring(0, 10) + " " + tv_end_time.getText().toString().substring(0, 5);
                if (name.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                if (content.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }
                createPicture(name, content);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_JOURNEY:
                    file_map_picture = new File(data.getStringExtra("picturePath"));
                    points = data.getStringExtra("points");
                    int w = getResources().getDisplayMetrics().widthPixels;
                    int h = (int) (getResources().getDisplayMetrics().density * 200);
                    Picasso.with(this)
                            .load(file_map_picture)
                            .resize(w, h)
                            .centerCrop()
                            .into(iv_map_picture);
                    break;
            }
        }
    }
}
