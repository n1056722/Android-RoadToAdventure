package ya.haojun.roadtoadventure.retrofit;


import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import ya.haojun.roadtoadventure.helper.URLHelper;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.FriendChat;
import ya.haojun.roadtoadventure.model.Group;
import ya.haojun.roadtoadventure.model.GroupChat;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.Picture;
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

    @POST("User/UpdatePassword")
    Call<User> updatePassword(@Body User user);

    @POST("User/ForgetPassword")
    Call<User> forgetPassword(@Body User user);

    @POST("User/VerifyCode")
    Call<User> verifyCode(@Body User user);

    @POST("User/ResetPassword")
    Call<User> resetPassword(@Body User user);

    @POST("User/UpdatePicture")
    Call<User> updatePicture(@Body User user);

    @Multipart
    @POST("Picture/Create")
    Call<Picture> createPicture(@Part MultipartBody.Part fileName,
                                @Part MultipartBody.Part subFileName,
                                @Part MultipartBody.Part type,
                                @Part MultipartBody.Part file);

    @POST("PersonalJourney/Create")
    Call<PersonalJourney> createPersonalJourney(@Body PersonalJourney personalJourney);

    @POST("PersonalJourney/GetList")
    Call<PersonalJourney> getPersonalJourneyList(@Body PersonalJourney personalJourney);

    @POST("PersonalJourney/Get")
    Call<PersonalJourney> getPersonalJourney(@Body PersonalJourney personalJourney);

    @POST("PersonalJourney/Start")
    Call<PersonalJourney> startPersonalJourney(@Body PersonalJourney personalJourney);

    @POST("PersonalJourney/End")
    Call<PersonalJourney> endPersonalJourney(@Body PersonalJourney personalJourney);

    @POST("Friend/GetFriendList")
    Call<Friend> getFriendsList(@Body Friend friend);

    @POST("Friend/Search")
    Call<Friend> searchFriends(@Body Friend friend);

    @POST("Friend/Create")
    Call<Friend> createFriend(@Body Friend friend);

    @POST("Friend/Delete")
    Call<Friend> deleteFriend(@Body Friend friend);

    @POST("FriendChat/GetList")
    Call<FriendChat> getFriendChatList(@Body Friend friend);

    @POST("Group/Create")
    Call<Group> createGroup(@Body Group group);

    @POST("Group/GetList")
    Call<Group> getGroupList(@Body User user);

    @POST("Group/Get")
    Call<Group> getGroup(@Body Group group);

    @POST("GroupChat/Create")
    Call<GroupChat> createGroupChat(@Body GroupChat groupChat);

    @POST("GroupChat/GetList")
    Call<GroupChat> getGroupChatList(@Body GroupChat groupChat);


}
