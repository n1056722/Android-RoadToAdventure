package ya.haojun.roadtoadventure.model;


public class MainItem {
    public static final String RECORD = "騎乘紀錄";
    public static final String POSITION = "即時定位";
    public static final String TIP = "路成功略";
    public static final String TOGETHER = "揪愛騎";
    public static final String CHAT = "聊天室";
    public static final String HELP = "急難救助";
    public static final String CHALLENGE = "路程挑戰";
    public static final String ROAD_QUERY = "路況查詢";
    private int icon;
    private String name;
    private int color;

    public MainItem(int icon, String name, int color) {
        this.icon = icon;
        this.name = name;
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
