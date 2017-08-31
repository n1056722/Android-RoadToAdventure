package ya.haojun.roadtoadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.EncryptHelper;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class ProfileActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private ImageView iv_picture;
    private TextView tv_name, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // ui reference
        iv_picture = (ImageView) findViewById(R.id.iv_profile_picture);
        tv_name = (TextView) findViewById(R.id.tv_profile_name);
        tv_email = (TextView) findViewById(R.id.tv_profile_email);
        findViewById(R.id.tv_profile_update_password).setOnClickListener(this);

        // init
        User u = User.getInstance();
        String picturePath = u.getUserPicture();
        if (!picturePath.isEmpty())
            Picasso.with(this).load(picturePath).into(iv_picture);
        tv_name.setText(u.getUserName());
        tv_email.setText(u.getEmail());

    }

    private void updatePassword(String oldPassword, String newPassword) {
        User params = new User();
        params.setUserId(User.getInstance().getUserId());
        params.setOldPassword(EncryptHelper.sha256(oldPassword));
        params.setNewPassword(EncryptHelper.sha256(newPassword));

        Call<User> call = RoadToAdventureService.service.updatePassword(params);
        showLoadingDialog();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    User result = response.body();
                    if (result.isSuccess()) {
                        t(R.string.success);
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

    private void showUpdatePasswordDialog() {
        final View v = LayoutInflater.from(this).inflate(R.layout.dialog_profile_update_password, null);
        alertWithView(v, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hideKeyBoard(v);
                String oldPassword = ((EditText) v.findViewById(R.id.et_dialog_profile_update_password_old_password)).getText().toString();
                String newPassword = ((EditText) v.findViewById(R.id.et_dialog_profile_update_password_new_password)).getText().toString();
                String confirmPassword = ((EditText) v.findViewById(R.id.et_dialog_profile_update_password_confirm_password)).getText().toString();

                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    t(R.string.confirm_password_error);
                    return;
                }

                updatePassword(oldPassword, newPassword);
            }
        }, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_profile_update_password:
                showUpdatePasswordDialog();
                break;
        }
    }
}
