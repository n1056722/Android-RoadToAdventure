package ya.haojun.roadtoadventure.retrofit;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
            .client(new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build())
            .build();

    RoadToAdventureService service = retrofit.create(RoadToAdventureService.class);

    @POST("User/Login")
    Call<User> login(@Body User user);

    @POST("User/SignUp")
    Call<User> signUp(@Body User user);

    @POST("User/ForgetPassword")
    Call<User> forgetPassword(@Body User user);

    @POST("User/VerifyCode")
    Call<User> verifyCode(@Body User user);

    @POST("User/ResetPassword")
    Call<User> resetPassword(@Body User user);

}
