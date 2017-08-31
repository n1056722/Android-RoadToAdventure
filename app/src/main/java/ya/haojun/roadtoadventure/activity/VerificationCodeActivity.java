package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class VerificationCodeActivity extends CommonActivity implements View.OnClickListener {

    // extra
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        findViewById(R.id.tv_verification_code_confirm).setOnClickListener(this);

        // extra
        userId = getIntent().getExtras().getString("userId");
    }

    private void verifyCode(final String code) {
        User params = new User();
        params.setUserId(userId);
        params.setVerificationCode(code);

        Call<User> call = RoadToAdventureService.service.verifyCode(params);
        showLoadingDialog();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    User result = response.body();
                    if (result.isSuccess()) {
                        Bundle b = new Bundle();
                        b.putString("userId", userId);
                        b.putString("verificationCode", code);
                        openActivity(ResetPasswordActivity.class, b);
                        finish();
                    } else {
                        t(R.string.fail);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        hideKeyBoard(v);
        switch (v.getId()) {
            case R.id.tv_verification_code_confirm:
                String code = ((EditText) findViewById(R.id.et_verification_code_code)).getText().toString();

                if (code.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                if (code.length() != 6) {
                    t(R.string.verification_code_format_error);
                    return;
                }

                verifyCode(code);
                break;
        }
    }
}
