package ya.haojun.roadtoadventure.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeHelper {
    private static final SimpleDateFormat sdf_standard = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf_no_year_second = new SimpleDateFormat("MM/dd HH:mm");

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
            int remainder = sec - (hour * 3600);
            int minute = remainder / 60;
            int second = remainder - (minute * 60);
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String convertToNoYearSecond(String date) {
        try {
            return sdf_no_year_second.format(sdf_standard.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long toSecond(String time) {
        try {
            return sdf_standard.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
