package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.LogHelper;

public class PictureActivity extends CommonActivity implements View.OnClickListener {

    private float scale = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        // ui
        ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture_picture);
        iv_picture.setOnClickListener(this);
        // extra
        String picturePath = getIntent().getExtras().getString("picturePath");

        // show
        Picasso.with(this)
                .load(picturePath)
                .into(iv_picture);
    }

    private long lastClick = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture_picture:
//                if (System.currentTimeMillis() - lastClick < 500L){
//                    Toast.makeText(this, "YA", Toast.LENGTH_SHORT).show();
//                }
//                lastClick = System.currentTimeMillis();
                break;
        }
    }
}
