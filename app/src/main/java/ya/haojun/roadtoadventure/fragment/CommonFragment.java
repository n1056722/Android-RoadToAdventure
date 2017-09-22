package ya.haojun.roadtoadventure.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Response;
import ya.haojun.roadtoadventure.R;

public class CommonFragment extends Fragment {

    public static final String TAG = "RoadToAdventure";

    private FragmentActivity activity;
    private ProgressDialog pd;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }

    public FragmentActivity getMyActivity() {
        return activity;
    }

    protected void showLoadingDialog() {
        showLoadingDialog(null);
    }

    protected void showLoadingDialog(String message) {
        if (pd == null) {
            pd = new ProgressDialog(activity);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
        }
        pd.setMessage(message != null ? message : getString(R.string.loading));
        pd.show();
    }

    protected void dismissLoadingDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    protected void log(String message) {
        Log.d(TAG, message);
    }

    protected void t(int textId) {
        t(getString(textId));
    }

    protected void t(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    protected void openActivity(Class activityClass) {
        openActivity(activityClass, null);
    }

    protected void openActivity(Class activityClass, Bundle bundle) {
        Intent intent = new Intent(activity, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected AlertDialog alertWithView(View v, DialogInterface.OnClickListener posi, DialogInterface.OnClickListener nega) {
        return new AlertDialog.Builder(activity)
                .setView(v)
                .setPositiveButton(R.string.confirm, posi)
                .setNegativeButton(R.string.cancel, nega)
                .show();
    }

    protected boolean isResponseOK(Response<?> response) {
        if (!response.isSuccessful()) {
            t(getString(R.string.connection_error) + response.code());
            return false;
        }
        if (response.body() == null) {
            t(R.string.server_error_null);
            return false;
        }
        return true;
    }
}
