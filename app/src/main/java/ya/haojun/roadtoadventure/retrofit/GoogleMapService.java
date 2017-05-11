package ya.haojun.roadtoadventure.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ya.haojun.roadtoadventure.model.GoogleDirection;
import ya.haojun.roadtoadventure.model.GooglePath;

public interface GoogleMapService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    GoogleMapService service = GoogleMapService.retrofit.create(GoogleMapService.class);

    @GET("elevation/json")
    Call<GooglePath> getElevations(@QueryMap Map<String, String> options);


    @GET("directions/json")
    Call<GoogleDirection> getDirections(@QueryMap Map<String, String> options);
}
