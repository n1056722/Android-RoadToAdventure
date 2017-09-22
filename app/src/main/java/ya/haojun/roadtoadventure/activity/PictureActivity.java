package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ya.haojun.roadtoadventure.R;

public class PictureActivity extends CommonActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        // ui
        ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture_picture);

        // extra
        String picturePath = getIntent().getExtras().getString("picturePath");

        // show
        Picasso.with(this)
                .load(picturePath)
                .into(iv_picture);
    }
}
