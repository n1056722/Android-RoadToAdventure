package ya.haojun.roadtoadventure.helper;

/**
 * Created by rin84 on 2017/10/1.
 */

public class CaloriesHelper {
    // Calories/Km/Hour
    public static final int K = 4;

    public static double calculate(int kg, double hour, double kmPerHour) {
        return K * kg * hour * (kmPerHour / 10.0);
    }
}
