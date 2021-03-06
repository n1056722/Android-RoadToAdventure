package ya.haojun.roadtoadventure.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeHelper {
    private static final SimpleDateFormat sdf_standard = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_MINUTE = new SimpleDateFormat("MM/dd HH:mm");
    private static final SimpleDateFormat DATE = new SimpleDateFormat("MM/dd");

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

    public static String toDateMinute(String date) {
        try {
            return DATE_MINUTE.format(sdf_standard.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String toDate(String date) {
        try {
            return DATE.format(sdf_standard.parse(date));
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

    public static String toChatFormat(String time) {
        try {
            Date d = sdf_standard.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            return c.get(Calendar.MONTH) + "/" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
