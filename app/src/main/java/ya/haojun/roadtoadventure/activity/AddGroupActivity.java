package ya.haojun.roadtoadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.GroupMemberRVAdapter;
import ya.haojun.roadtoadventure.helper.BitmapHelper;
import ya.haojun.roadtoadventure.helper.FileHelper;
import ya.haojun.roadtoadventure.helper.GenericFileProvider;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.Group;
import ya.haojun.roadtoadventure.model.GroupMember;
import ya.haojun.roadtoadventure.model.Group;
import ya.haojun.roadtoadventure.model.Picture;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class AddGroupActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int REQUEST_FROM_GALLERY = 1;
    public static final int REQUEST_TAKE_PHOTO = 2;
    public static final int REQUEST_TAKE_CROP = 3;
    public static final int REQUEST_INVITE_MEMBER = 4;
    // ui
    private ImageView iv_picture;
    private EditText et_name;
    private RecyclerView rv_member;
    // data
    private ArrayList<GroupMember> list_member;
    private File file_group_picture;
    // take picture
    private Uri uriTakePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        // ui reference
        iv_picture = (ImageView) findViewById(R.id.iv_add_group_picture);
        et_name = (EditText) findViewById(R.id.et_add_group_name);
        rv_member = (RecyclerView) findViewById(R.id.rv_add_group_member);
        findViewById(R.id.iv_add_group_done).setOnClickListener(this);
        iv_picture.setOnClickListener(this);
        // init RecyclerView
        list_member = new ArrayList<>();
        rv_member.setLayoutManager(new GridLayoutManager(this, 3));
        rv_member.setAdapter(new GroupMemberRVAdapter(this, list_member));
    }

    private void createPicture(final String name) {
        if (file_group_picture == null) {
            t("未選擇圖片");
            return;
        }
        MultipartBody.Part fileName = MultipartBody.Part.createFormData("fileName", UUID.randomUUID().toString());
        MultipartBody.Part subFileName = MultipartBody.Part.createFormData("subFileName", "jpg");
        MultipartBody.Part type = MultipartBody.Part.createFormData("type", "1");
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file_group_picture.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file_group_picture));

        Call<Picture> call = RoadToAdventureService.service.createPicture(fileName, subFileName, type, body);
        showLoadingDialog();
        call.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Picture result = response.body();
                    if (result.isSuccess()) {
                        createGroup(name, result.getPicturePath());
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

    private void createGroup(String name, String picturePath) {
        Group params = new Group();
        params.setUserId(User.getInstance().getUserId());
        params.setName(name);
        params.setPicturePath(picturePath);
        params.getMembers().addAll(list_member);

        Call<Group> call = RoadToAdventureService.service.createGroup(params);
        showLoadingDialog();
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    Group result = response.body();
                    if (result.isSuccess()) {
                        t(R.string.success);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        t(R.string.fail);
                        LogHelper.d(result.getMessage()+"");
                    }
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
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
                    uriTakePicture = FileProvider.getUriForFile(AddGroupActivity.this, GenericFileProvider.AUTH, new File(FileHelper.getPicturesDir(AddGroupActivity.this) + "/take_picture.jpg"));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTakePicture);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_group_done:
                String name = et_name.getText().toString();
                if (name.isEmpty()) {
                    t(R.string.empty_error);
                    return;
                }
                createPicture(name);
                break;
            case R.id.iv_add_group_picture:
                choosePicture();
                break;
        }
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
                file_group_picture = new File(data.getStringExtra("path"));
                int w = (int) (getResources().getDisplayMetrics().density * 100);
                Picasso.with(this)
                        .load(file_group_picture)
                        .resize(w, w)
                        .centerCrop()
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(iv_picture);
                break;
            case REQUEST_INVITE_MEMBER:
                ArrayList<Friend> list = data.getParcelableArrayListExtra("members");
                list_member.clear();
                for (Friend f : list){
                    list_member.add(new GroupMember(f.getUserId(), f.getUserName(), f.getUserPicture()));
                }
                rv_member.getAdapter().notifyDataSetChanged();
                break;
        }
    }
}
