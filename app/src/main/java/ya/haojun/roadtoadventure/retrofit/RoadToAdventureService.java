package ya.haojun.roadtoadventure.retrofit;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ya.haojun.roadtoadventure.helper.URLHelper;
import ya.haojun.roadtoadventure.model.User;

public interface RoadToAdventureService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URLHelper.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RoadToAdventureService service = retrofit.create(RoadToAdventureService.class);

    @POST("User/Login")
    Call<User> login(@Body User user);

    @POST("User/SignUp")
    Call<User> signUp(@Body User user);
}
