package ya.haojun.roadtoadventure.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ya.haojun.roadtoadventure.helper.URLHelper;

public interface RoadToAdventureService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URLHelper.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
