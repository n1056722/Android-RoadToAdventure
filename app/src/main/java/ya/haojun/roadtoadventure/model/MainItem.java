package ya.haojun.roadtoadventure.model;

public class MainItem {
    public static final String RECORD = "騎乘紀錄";
    public static final String POSITION = "即時定位";
    public static final String DISCUSSION = "路程攻略";
    public static final String TOGETHER = "揪愛騎";
    public static final String CHAT = "聊天室";
    public static final String HELP = "急難救助";
    public static final String CHALLENGE = "路程挑戰";
    public static final String ROAD_QUERY = "路況查詢";
    public static final String GROUP = "群組功能";
    private int icon;
    private String name;
    private int backgroundColor;
    private int textColor;

    public MainItem(int icon, String name, int backgroundColor, int textColor) {
        this.icon = icon;
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
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

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
