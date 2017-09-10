package ya.haojun.roadtoadventure.model;

/**
 * Created by haojun on 2017/9/10.
 */

public class Picture extends CommonModel {
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
