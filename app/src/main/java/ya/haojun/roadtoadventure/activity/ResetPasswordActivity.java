package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.EncryptHelper;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class ResetPasswordActivity extends CommonActivity implements View.OnClickListener {

    // extra
    private String userId;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        findViewById(R.id.tv_reset_password_confirm).setOnClickListener(this);

        // extra
        userId = getIntent().getExtras().getString("userId");
        verificationCode = getIntent().getExtras().getString("verificationCode");
    }

    private void resetPassword(String password) {
        User params = new User();
        params.setUserId(userId);
        params.setVerificationCode(verificationCode);
        params.setNewPassword(EncryptHelper.sha256(password));

        Call<User> call = RoadToAdventureService.service.resetPassword(params);
        showLoadingDialog();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    User result = response.body();
                    if (result.isSuccess()) {
                        t(R.string.success);
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
            case R.id.tv_reset_password_confirm:
                String password = ((EditText) findViewById(R.id.et_reset_password_password)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.et_reset_password_confirm_password)).getText().toString();

                if (userId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    t(R.string.confirm_password_error);
                    return;
                }

                resetPassword(password);
                break;
        }
    }
}
