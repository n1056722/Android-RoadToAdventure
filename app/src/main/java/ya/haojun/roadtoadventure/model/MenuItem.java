package ya.haojun.roadtoadventure.model;

import yalantis.com.sidemenu.interfaces.Resourceble;

/**
 * Created by asus on 2017/3/9.
 */

public class MenuItem implements Resourceble{
    public static final String CLOSE = "close";
    public static final String PERSONAL = "personal";
    public static final String GROUP = "group";
    public static final String CHALLENGE = "challenge";
    public static final String INTERACTIVE= "interactive";
    public static final String SHOPPING= "shopping";
    public static final String SETTING= "setting";
    public static final String EXIT= "exit";


    private String name;
    private int imageRes;

    public MenuItem(String name, int imageRes) {
        this.name = name;
        this.imageRes = imageRes;
    }

    @Override
    public int getImageRes() {
        return imageRes;
    }

    @Override
    public String getName() {
        return name;
    }
}
