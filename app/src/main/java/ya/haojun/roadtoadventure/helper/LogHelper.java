package ya.haojun.roadtoadventure.helper;


import android.util.Log;

public class LogHelper {
    public static final String TAG = "RTA";
    private static boolean isDebuggable = true;

    public static void setDebuggable(boolean d) {
        isDebuggable = d;
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (!isDebuggable) return;
        Log.d(tag, msg);
    }
}
