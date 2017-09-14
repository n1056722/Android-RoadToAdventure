package ya.haojun.roadtoadventure.model;

/**
 * Created by haojun on 2017/9/10.
 */

public class Picture extends CommonModel {
    public static final String USER = "0";
    public static final String GROUP = "1";
    public static final String PERSONAL_JOURNEY = "2";
    public static final String GROUP_JOURNEY = "3";

    private String picturePath;

    public Picture() {

    }

    public Picture(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
