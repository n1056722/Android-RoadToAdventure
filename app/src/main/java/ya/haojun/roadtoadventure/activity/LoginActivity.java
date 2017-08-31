package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
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

public class LoginActivity extends CommonActivity implements View.OnClickListener {

    public static final int REQUEST_SIGN_UP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.tv_login_do_login).setOnClickListener(this);
        findViewById(R.id.tv_login_sign_up).setOnClickListener(this);
        findViewById(R.id.tv_login_forget_password).setOnClickListener(this);
    }

    private void login(String userId, String password) {
        User params = new User();
        params.setUserId(userId);
        params.setPassword(EncryptHelper.sha256(password));

        Call<User> call = RoadToAdventureService.service.login(params);
        showLoadingDialog();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    User result = response.body();
                    if (result.isSuccess()) {
                        User user = User.getInstance();
                        user.setUserId(result.getUserId());
                        user.setUserName(result.getUserName());
                        user.setUserPicture(result.getUserPicture());
                        user.setEmail(result.getEmail());
                        user.setModifyDate(result.getModifyDate());
                        SPHelper.setUser(LoginActivity.this);
                        openActivity(MainActivity.class);
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
            case R.id.tv_login_do_login:
                String userId = ((EditText) findViewById(R.id.et_login_user_id)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_login_password)).getText().toString();

                if (userId.isEmpty() || password.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                login(userId, password);
                break;
            case R.id.tv_login_sign_up:
                openActivityForResult(SignUpActivity.class, REQUEST_SIGN_UP);
                break;
            case R.id.tv_login_forget_password:
                openActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_SIGN_UP:
                ((EditText) findViewById(R.id.et_login_user_id)).setText(data.getStringExtra("userId"));
                ((EditText) findViewById(R.id.et_login_password)).setText("");
                break;
        }
    }
}
