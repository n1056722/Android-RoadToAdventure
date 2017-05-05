package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.User;

public class LoginActivity extends CommonActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.tv_login_do_login).setOnClickListener(this);
        findViewById(R.id.tv_login_sign_up).setOnClickListener(this);
        findViewById(R.id.tv_login_forget_password).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_do_login:
//                String userName =((EditText)findViewById(R.id.et_login_user_name)).getText().toString();
//                String userPassword =((EditText)findViewById(R.id.et_login_user_password)).getText().toString();

                User user = User.getInstance();
                user.setUserID("hj");
                user.setUserName("haojun");
                user.setUserPicture("https://avatars1.githubusercontent.com/u/15250400?v=3&s=460");

                openActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_login_sign_up:
                openActivity(SignUpActivity.class);
                break;
            case R.id.tv_login_forget_password:
                break;
        }
    }
}
