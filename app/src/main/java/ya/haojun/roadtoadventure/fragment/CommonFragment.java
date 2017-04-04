package ya.haojun.roadtoadventure.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import ya.haojun.roadtoadventure.R;

/**
 * Created by asus on 2017/3/11.
 */

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
        pd.setMessage(message != null ? message : getString(R.string.msg_loading));
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

    protected void t(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
