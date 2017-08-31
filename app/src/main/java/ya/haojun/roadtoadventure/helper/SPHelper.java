package ya.haojun.roadtoadventure.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import ya.haojun.roadtoadventure.model.User;

/**
 * Created by asus on 2017/4/6.
 */

public class SPHelper {
    public static final String FILE_SERVICE = "service";
    public static final String SERVICE_IS_START = "isStart";

    public static void setServiceStatus(Context context, boolean start) {
        SharedPreferences sp = context.getSharedPreferences(FILE_SERVICE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(SERVICE_IS_START, start).apply();
    }

    public static boolean getServiceStatus(Context context) {
        return context.getSharedPreferences(FILE_SERVICE, Context.MODE_PRIVATE).getBoolean(SERVICE_IS_START, false);
    }

    private static final String FILE_USER = "user";
    private static final String USER = "user";

    public static void setUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        sp.edit().putString(USER, User.getInstance().getJSONObject().toString()).apply();
    }

    public static boolean getUser(Context context) {
        try {
            User u = User.getInstance();
            u.setJSONObject(new JSONObject(context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE).getString(USER, "")));
            return !u.getUserId().isEmpty();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void clearUser(Context context) {
        context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
