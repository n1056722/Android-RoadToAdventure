package ya.haojun.roadtoadventure.model;


public class DrawerItem {
    public static final String PERSONAL = "個人騎乘";
    public static final String PERSONAL_RECORD = "騎乘紀錄";
    public static final String PERSONAL_FIREND = "我的好友";

    public static final String GROUP = "團體騎乘";
    public static final String GROUP_MY = "我的群組";

    public static final String CHALLENGE = "路程挑戰";
    public static final String CHALLENGE_MY = "個人挑戰";
    public static final String CHALLENGE_GROUP = "團體挑戰";

    public static final String ROAD_QUERY = "路況查詢";
    public static final String DISCUSSION = "路程功略";
    public static final String HELP = "急難救助";

    private int icon;
    private String name;

    public DrawerItem(){

    }

    public DrawerItem(int icon, String name) {
        this.icon = icon;
        this.name = name;
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
}
