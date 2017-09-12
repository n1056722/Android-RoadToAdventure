package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImageView;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.BitmapHelper;

public class CropActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private CropImageView civ;

    // extra
    private Uri uri;
    private int ratioX, ratioY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        // ui reference
        civ = (CropImageView) findViewById(R.id.civ);
        findViewById(R.id.tv_crop_rotation).setOnClickListener(this);
        findViewById(R.id.tv_crop_confirm).setOnClickListener(this);

        // extra
        uri = Uri.parse(getIntent().getExtras().getString("uri"));
        ratioX = getIntent().getExtras().getInt("ratioX");
        ratioY = getIntent().getExtras().getInt("ratioY");

        // init
        civ.setImageUriAsync(uri);
        if (ratioX != 0 && ratioY != 0)
            civ.setAspectRatio(ratioX, ratioY);
    }

    private class SaveCropFile extends AsyncTask<Void, Void, String> {
        Bitmap bitmap;

        SaveCropFile() {
            this.bitmap = civ.getCroppedImage();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog(getString(R.string.loading));
        }

        @Override
        protected String doInBackground(Void... params) {
            return BitmapHelper.bitmap2JPGFile(CropActivity.this, bitmap, "crop").getAbsolutePath();
        }

        @Override
        protected void onPostExecute(String path) {
            super.onPostExecute(path);
            dismissLoadingDialog();
            Intent intent = new Intent();
            intent.putExtra("path", path);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_crop_rotation:
                civ.setRotatedDegrees(civ.getRotatedDegrees() + 90);
                break;
            case R.id.tv_crop_confirm:
                new SaveCropFile().execute();
                break;
        }
    }
}
