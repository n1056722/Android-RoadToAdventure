package ya.haojun.roadtoadventure.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ya.haojun.roadtoadventure.R;

public class LoginActivity extends CommonActivity {

    private static int REQUEST_PERMISSION = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkPermission();
    }

    private void checkPermission() {
        final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean check = false;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                check = true;
                break;
            }
        }
        if (check) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        }else{
            openActivity(MainActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            boolean pass = true;
            for (int g : grantResults) {
                if (g != PackageManager.PERMISSION_GRANTED) {
                    pass = false;
                    break;
                }
            }
            if (pass) {
                openActivity(MainActivity.class);
            }
            finish();
        }
    }
}
