package ya.haojun.roadtoadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.EncryptHelper;
import ya.haojun.roadtoadventure.helper.FileHelper;
import ya.haojun.roadtoadventure.helper.GenericFileProvider;
import ya.haojun.roadtoadventure.model.Picture;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class ProfileActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int REQUEST_FROM_GALLERY = 1;
    public static final int REQUEST_TAKE_PHOTO = 2;
    public static final int REQUEST_TAKE_CROP = 3;
    // ui
    private ImageView iv_picture;
    private TextView tv_name, tv_email;
    // data
    private File file_user_picture;
    private Uri uriTakePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // ui reference
        iv_picture = (ImageView) findViewById(R.id.iv_profile_picture);
        tv_name = (TextView) findViewById(R.id.tv_profile_name);
        tv_email = (TextView) findViewById(R.id.tv_profile_email);
        findViewById(R.id.tv_profile_update_password).setOnClickListener(this);
        iv_picture.setOnClickListener(this);
        // init
        User u = User.getInstance();
        String picturePath = u.getUserPicture();
        if (!picturePath.isEmpty()) {
            int w = (int) (getResources().getDisplayMetrics().density * 100);
            Picasso.with(this)
                    .load(picturePath)
                    .resize(w, w)
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(iv_picture);
        }
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

    private void createPicture(final String name) {
        if (file_user_picture == null) {
            t("未選擇圖片");
            return;
        }
        MultipartBody.Part fileName = MultipartBody.Part.createFormData("fileName", UUID.randomUUID().toString());
        MultipartBody.Part subFileName = MultipartBody.Part.createFormData("subFileName", "jpg");
        MultipartBody.Part type = MultipartBody.Part.createFormData("type", Picture.USER);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file_user_picture.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file_user_picture));

        Call<Picture> call = RoadToAdventureService.service.createPicture(fileName, subFileName, type, body);
        showLoadingDialog();
        call.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Picture result = response.body();
                    if (result.isSuccess()) {

                        
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

    private void choosePicture() {
        alertWithItems(getResources().getStringArray(R.array.choose_picture), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_FROM_GALLERY);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    uriTakePicture = FileProvider.getUriForFile(ProfileActivity.this, GenericFileProvider.AUTH, new File(FileHelper.getPicturesDir(ProfileActivity.this) + "/take_picture.jpg"));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTakePicture);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_profile_picture:
                choosePicture();
                break;
            case R.id.tv_profile_update_password:
                showUpdatePasswordDialog();
                break;
        }
    }

    private void showPictureDialog() {
        ImageView iv = new ImageView(this);
        int w = (int) (getResources().getDisplayMetrics().density * 100);
        Picasso.with(this)
                .load(file_user_picture)
                .resize(w, w)
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(iv);
        alertWithView(iv, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        Bundle b = new Bundle();
        switch (requestCode) {
            case REQUEST_FROM_GALLERY:
                b.putString("uri", data.getData().toString());
                b.putInt("ratioX", 1);
                b.putInt("ratioY", 1);
                openActivityForResult(CropActivity.class, REQUEST_TAKE_CROP, b);
                break;
            case REQUEST_TAKE_PHOTO:
                b.putString("uri", uriTakePicture.toString());
                b.putInt("ratioX", 1);
                b.putInt("ratioY", 1);
                openActivityForResult(CropActivity.class, REQUEST_TAKE_CROP, b);
                break;
            case REQUEST_TAKE_CROP:
                file_user_picture = new File(data.getStringExtra("path"));
                showPictureDialog();
                break;
        }
    }
}
