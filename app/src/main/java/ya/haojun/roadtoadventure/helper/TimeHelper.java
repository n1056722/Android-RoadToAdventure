package ya.haojun.roadtoadventure.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asus on 2017/4/5.
 */

public class TimeHelper {
    private static final SimpleDateFormat sdf_standard = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String now() {
        return sdf_standard.format(new Date());
    }
}
