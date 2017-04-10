package ya.haojun.roadtoadventure.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ya.haojun.roadtoadventure.R;

/**
 * Created by asus on 2017/3/5.
 */

public class CommonActivity extends AppCompatActivity {

    public static final String TAG = "RoadToAdventure";

    ProgressDialog pd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void alert(String title, String message, DialogInterface.OnClickListener posi, DialogInterface.OnClickListener nega) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_bt_confirm, posi)
                .setNegativeButton(R.string.alert_bt_cancel, nega)
                .show();
    }

    protected void alertWithView(View v, DialogInterface.OnClickListener posi, DialogInterface.OnClickListener nega) {
        new AlertDialog.Builder(this)
                .setView(v)
                .setPositiveButton(R.string.alert_bt_confirm, posi)
                .setNegativeButton(R.string.alert_bt_cancel, nega)
                .show();
    }

    protected void alertWithView(View v) {
        new AlertDialog.Builder(this)
                .setView(v)
                .show();
    }

    protected void alertWithItem(String[] items, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setItems(items, listener)
                .show();
    }

    protected void t(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showLoadingDialog() {
        showLoadingDialog(null);
    }

    protected void showLoadingDialog(String message) {
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
        }
        pd.setMessage(message != null ? message : getString(R.string.msg_loading));
        pd.show();
    }

    protected void dismissLoadingDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    protected void openActivity(Class activityClass) {
        openActivity(activityClass, null);
    }

    protected void openActivity(Class activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void openActivityForResult(Class activityClass, int request) {
        openActivityForResult(activityClass, request, null);
    }

    protected void openActivityForResult(Class activityClass, int request, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, request);
    }

    protected void log(String message) {
        Log.d(TAG, message);
    }
}
