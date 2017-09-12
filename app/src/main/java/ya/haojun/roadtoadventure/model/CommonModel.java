package ya.haojun.roadtoadventure.model;

/**
 * Created by haojun on 2017/8/24.
 */

public class CommonModel {
    private int result;
    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isSuccess(){
        return result == 1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
