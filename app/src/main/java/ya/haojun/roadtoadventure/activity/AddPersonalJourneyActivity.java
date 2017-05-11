package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.JourneyOpenListAdapter;
import ya.haojun.roadtoadventure.helper.BitmapHelper;

public class AddPersonalJourneyActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int SELECT_JOURNEY = 0;

    // ui
    private Spinner sp_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_journey);

        // ui reference
        sp_open = (Spinner) findViewById(R.id.sp_add_personal_journey_open);
        findViewById(R.id.tv_add_personal_journey_direction).setOnClickListener(this);

        // init Spinner
        sp_open.setAdapter(new JourneyOpenListAdapter(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_personal_journey_direction:
                openActivityForResult(SelectDistanceMapActivity.class, SELECT_JOURNEY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_JOURNEY:
                    ImageView iv = (ImageView) findViewById(R.id.iv_add_personal_journey_image);
                    iv.setImageBitmap(BitmapHelper.byteArrayToBitmap(data.getByteArrayExtra("image")));
                    break;
            }
        }
    }
}
