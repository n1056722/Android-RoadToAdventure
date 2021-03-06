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
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class ForgetPasswordActivity extends CommonActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findViewById(R.id.tv_forget_password_confirm).setOnClickListener(this);
    }

    private void forgetPassword(final String userId, String email) {
        User params = new User();
        params.setUserId(userId);
        params.setEmail(email);

        Call<User> call = RoadToAdventureService.service.forgetPassword(params);
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
                        openActivity(VerificationCodeActivity.class, b);
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
        hideKeyBoard(v);
        switch (v.getId()) {
            case R.id.tv_forget_password_confirm:
                String userId = ((EditText) findViewById(R.id.et_forget_password_user_id)).getText().toString();
                String email = ((EditText) findViewById(R.id.et_forget_password_email)).getText().toString();

                if (userId.isEmpty() || email.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                if (!isValidEmail(email)) {
                    t(R.string.email_error);
                    return;
                }

                forgetPassword(userId, email);
                break;
        }
    }
}
