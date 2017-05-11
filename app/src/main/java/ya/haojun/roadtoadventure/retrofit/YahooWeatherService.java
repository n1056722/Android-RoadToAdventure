package ya.haojun.roadtoadventure.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import ya.haojun.roadtoadventure.model.GoogleDirection;
import ya.haojun.roadtoadventure.model.GooglePath;

public interface YahooWeatherService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://query.yahooapis.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    YahooWeatherService service = YahooWeatherService.retrofit.create(YahooWeatherService.class);

    @GET("v1/public/yql")
    Call<String> getWeather(@QueryMap Map<String, String> options);
}
