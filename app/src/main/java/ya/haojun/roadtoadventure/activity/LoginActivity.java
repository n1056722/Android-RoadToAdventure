package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ya.haojun.roadtoadventure.R;

public class LoginActivity extends CommonActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.tv_login_do_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_do_login:
//                String userName =((EditText)findViewById(R.id.et_login_user_name)).getText().toString();
//                String userPassword =((EditText)findViewById(R.id.et_login_user_password)).getText().toString();
                openActivity(MainActivity.class);
                finish();
                break;
        }
    }
}
