package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ya.haojun.roadtoadventure.R;

public class GroupInfoActivity extends AppCompatActivity {

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
        // extra
        groupID = getIntent().getExtras().getInt("groupID");
    }
}
