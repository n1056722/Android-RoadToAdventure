package ya.haojun.roadtoadventure.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.LocationRecordModel;
import ya.haojun.roadtoadventure.sqlite.DAOLocationRecord;


public class LocationService extends Service {
    public static final String ACTION_LOCATION = "ya.haojun.roadtoadventure.service";
    // data
    private Intent intent;
    //
    private static final String TAG = "LocationService";
    private LocationManager mLocationManager;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 0;

    private class MyLocationListener implements LocationListener {

        public MyLocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            String time = TimeHelper.now();
            new DAOLocationRecord(getApplicationContext()).insert(new LocationRecordModel(lat,lng,time));
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new MyLocationListener(LocationManager.GPS_PROVIDER),
            new MyLocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        intent = new Intent(ACTION_LOCATION);
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // gps
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[0]);
            // network
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        if (mLocationManager != null) {
            for (LocationListener mLocationListener : mLocationListeners) {
                try {
                    mLocationManager.removeUpdates(mLocationListener);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }
}
