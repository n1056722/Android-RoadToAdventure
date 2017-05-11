package ya.haojun.roadtoadventure.helper;

import java.util.HashMap;
import java.util.Map;

import ya.haojun.roadtoadventure.R;


public class YahooWeatherHelper {

    public static Map<String, String> getWeatherQuery(double lat, double lng) {
        String str_lat = String.format("%.6f", lat);
        String str_lng = String.format("%.6f", lng);
        Map<String, String> map = new HashMap<>();
        map.put("q", String.format("select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"(%s,%s)\")", str_lat, str_lng));
        map.put("format", "json");
        return map;
    }

    public static String getWeatherName(String code) {
        switch (code) {
            case "0":
                return "龍捲風";
            case "1":
                return "熱帶風暴";
            case "2":
                return "颶風";
            case "3":
                return "強雷陣雨";
            case "4":
                return "雷陣雨";
            case "5":
                return "混合雨雪";
            case "6":
                return "混合雨雪";
            case "7":
                return "混合雨雪";
            case "8":
                return "冰凍小雨";
            case "9":
                return "細雨";
            case "10":
                return "凍雨";
            case "11":
                return "陣雨";
            case "12":
                return "陣雨";
            case "13":
                return "飄雪";
            case "14":
                return "陣雪";
            case "15":
                return "吹雪";
            case "16":
                return "下雪";
            case "17":
                return "冰雹";
            case "18":
                return "雨雪";
            case "19":
                return "多塵";
            case "20":
                return "多霧";
            case "21":
                return "陰霾";
            case "22":
                return "多煙";
            case "23":
                return "狂風大作";
            case "24":
                return "有風";
            case "25":
                return "冷";
            case "26":
                return "多雲";
            case "27":
                return "晴間多雲（夜）";
            case "28":
                return "晴間多雲（日）";
            case "29":
                return "晴間多雲（夜）";
            case "30":
                return "晴間多雲（日）";
            case "31":
                return "清晰的（夜）";
            case "32":
                return "晴朗";
            case "33":
                return "晴朗（夜）";
            case "34":
                return "晴朗（日）";
            case "35":
                return "雨和冰雹";
            case "36":
                return "炎熱";
            case "37":
                return "雷陣雨";
            case "38":
                return "零星雷陣雨";
            case "39":
                return "零星雷陣雨";
            case "40":
                return "零星雷陣雨";
            case "41":
                return "大雪";
            case "42":
                return "零星陣雪";
            case "43":
                return "大雪";
            case "44":
                return "多雲";
            case "45":
                return "雷陣雨";
            case "46":
                return "陣雪";
            case "47":
                return "雷陣雨";
            case "3200":
                return "資料錯誤";
            default:
                return code;
        }
    }

    public static int getWeatherPicture(String code) {
        switch (code) {
            case "3":
            case "4":
            case "45":
            case "47":
                return R.drawable.wh_thunder;
            case "9":
            case "10":
            case "11":
            case "12":
                return R.drawable.wh_rain;
            case "19":
            case "20":
            case "21":
            case "22":
                return R.drawable.wh_haze;
            case "23":
            case "24":
            case "37":
            case "38":
            case "39":
            case "40":
                return R.drawable.wh_wind;
            case "26":
            case "27":
            case "28":
            case "29":
            case "30":
            case "44":
                return R.drawable.wh_double_cloud;
            case "31":
            case "32":
            case "33":
            case "34":
            case "36":
                return R.drawable.wh_sun;
            default:
                return R.drawable.ic_clear_w;
        }
    }

    public static String getWeatherTemp(String code) {
        int f = Integer.valueOf(code);
        int c = (f - 32) * 5 / 9;
        return c + "ºC";
    }
}
