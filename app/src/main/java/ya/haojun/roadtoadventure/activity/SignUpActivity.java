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
import ya.haojun.roadtoadventure.helper.SPHelper;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class SignUpActivity extends CommonActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // ui reference
        findViewById(R.id.tv_sign_up_do_sign_up).setOnClickListener(this);
    }

    private void signUp(final String userId, String password, String name, String email) {
        User params = new User();
        params.setUserId(userId);
        params.setPassword(EncryptHelper.sha256(password));
        params.setUserName(name);
        params.setEmail(email);

        Call<User> call = RoadToAdventureService.service.signUp(params);
        showLoadingDialog();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    User result = response.body();
                    if (result.isSuccess()) {
                        Intent intent = new Intent();
                        intent.putExtra("userId", userId);
                        setResult(RESULT_OK, intent);
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

    public boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sign_up_do_sign_up:
                String userId = ((EditText) findViewById(R.id.et_sign_up_user_id)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_sign_up_password)).getText().toString();
                String confirmPassword = ((EditText) findViewById(R.id.et_sign_up_confirm_password)).getText().toString();
                String name = ((EditText) findViewById(R.id.et_sign_up_name)).getText().toString();
                String email = ((EditText) findViewById(R.id.et_sign_up_email)).getText().toString();

                if (userId.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || email.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    t(R.string.confirm_password_error);
                    return;
                }

                if (!isValidEmail(email)) {
                    t(R.string.email_error);
                    return;
                }

                signUp(userId, password, name, email);
                break;
        }
    }
}
