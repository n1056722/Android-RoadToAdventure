package ya.haojun.roadtoadventure.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by asus on 2017/4/6.
 */

public class SPHelper {
    public static final String FILE_SERVICE = "service";
    public static final String SERVICE_ISSTART = "isStart";

    public static void setServiceStatus(Context context, boolean start) {
        SharedPreferences sp = context.getSharedPreferences(FILE_SERVICE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(SERVICE_ISSTART, start).apply();
    }

    public static boolean getServiceStatus(Context context) {
        return context.getSharedPreferences(FILE_SERVICE, Context.MODE_PRIVATE).getBoolean(SERVICE_ISSTART, false);
    }
}
