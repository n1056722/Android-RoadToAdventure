package ya.haojun.roadtoadventure.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ya.haojun.roadtoadventure.model.GoogleDirection;
import ya.haojun.roadtoadventure.model.GooglePath;

/**
 * Created by asus on 2017/3/8.
 */

public interface GoogleMapService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("elevation/json")
    Call<GooglePath> getElevations(@QueryMap Map<String, String> options);


    @GET("directions/json")
    Call<GoogleDirection> getDirections(@QueryMap Map<String, String> options);
}
