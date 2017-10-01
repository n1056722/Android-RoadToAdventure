package ya.haojun.roadtoadventure.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.SPHelper;
import ya.haojun.roadtoadventure.service.LocationService;

public class PermissionActivity extends CommonActivity {

    // request
    public static final int PERMISSION = 0;

    // permission
    public static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        checkPermission();
    }

    private void checkPermission() {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION);
                return;
            }
        }

        if (!SPHelper.getServiceStatus(this) || !isMyServiceRunning(LocationService.class))
            startLocationService();
        if (SPHelper.getUser(this)){
            openActivity(MainActivity.class);
        }else{
        openActivity(LoginActivity.class);
        }
        finish();
    }

    private void startLocationService() {
        Intent intent = new Intent();
        intent.setClass(this, LocationService.class);
        startService(intent);
        SPHelper.setServiceStatus(this, true);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                t(R.string.permission_denied);
                finish();
                return;
            }
        }
        checkPermission();
    }
}
