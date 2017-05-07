package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ya.haojun.roadtoadventure.R;

public class GroupInfoActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private ImageView iv_group_picture;
    private TextView tv_group_name;
    // extra
    private int groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        // ui reference
        iv_group_picture = (ImageView) findViewById(R.id.iv_group_info_picture);
        tv_group_name = (TextView) findViewById(R.id.tv_group_info_name);
        findViewById(R.id.cv_group_info_member).setOnClickListener(this);
        findViewById(R.id.cv_group_info_chat).setOnClickListener(this);
        // extra
        groupID = getIntent().getExtras().getInt("groupID");
        // init
        int w = getResources().getDisplayMetrics().widthPixels;
        int h = (int) (getResources().getDisplayMetrics().density * 192);
        Picasso.with(this).load("http://barkpost-assets.s3.amazonaws.com/wp-content/uploads/2013/11/muchdoge-700x393.jpg").resize(w, h).centerCrop().into(iv_group_picture);
        tv_group_name.setText("熱血騎士");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_group_info_member:
                openActivity(GroupMemberActivity.class);
                break;
            case R.id.cv_group_info_chat:
                break;
        }
    }
}
