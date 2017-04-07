package ya.haojun.roadtoadventure.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeHelper {
    private static final SimpleDateFormat sdf_standard = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String now() {
        return sdf_standard.format(new Date());
    }

    public static String gap(String from, String to) {
        try {
            Date f = sdf_standard.parse(from);
            Date t = sdf_standard.parse(to);
            long gap = t.getTime() - f.getTime();
            int sec = (int) (gap / 1000);
            int hour = sec / 3600;
            int minute = sec / 60;
            int second = sec % 60;
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
