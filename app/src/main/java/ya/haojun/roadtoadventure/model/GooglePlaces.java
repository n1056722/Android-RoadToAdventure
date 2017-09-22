package ya.haojun.roadtoadventure.model;

import java.util.ArrayList;

/**
 * Created by rin84 on 2017/9/22.
 */

public class GooglePlaces {
    private ArrayList<GooglePlace> results;
    public GooglePlaces(){
        results = new ArrayList<>();
    }

    public ArrayList<GooglePlace> getResults() {
        return results;
    }
}
