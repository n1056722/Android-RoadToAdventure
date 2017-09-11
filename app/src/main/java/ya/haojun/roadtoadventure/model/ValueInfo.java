package ya.haojun.roadtoadventure.model;

/**
 * Created by haojun on 2017/9/11.
 */

public class ValueInfo {
    private String title;
    private String value;

    public ValueInfo() {

    }

    public ValueInfo(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
