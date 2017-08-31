package ya.haojun.roadtoadventure.model;

/**
 * Created by haojun on 2017/8/24.
 */

public class CommonModel {
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isSuccess(){
        return result == 1;
    }
}
