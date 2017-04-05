package ya.haojun.roadtoadventure.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.SPHelper;
import ya.haojun.roadtoadventure.service.LocationService;

public class PermissionActivity extends AppCompatActivity {

    // permission
    public static final int PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        // check permission
        checkPermission();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION);
        } else {
            Intent intent = new Intent();
            if (SPHelper.getServiceStatus(this)) {
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                // start service
                intent = new Intent();
                intent.setClass(this, LocationService.class);
                startService(intent);
                SPHelper.setServiceStatus(this, true);
                // reCheck
                checkPermission();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION) {
            boolean pass = true;
            for (int g : grantResults) {
                if (g != PackageManager.PERMISSION_GRANTED) {
                    pass = false;
                    break;
                }
            }
            if (pass) checkPermission();
        }
    }
}
