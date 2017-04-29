package ya.haojun.roadtoadventure.model;


public class DrawerItem {
    public static final String SIGN_OUT = "Sign Out";
    private int icon;
    private String name;

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
