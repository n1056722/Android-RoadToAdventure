package ya.haojun.roadtoadventure.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Friend extends CommonModel implements Parcelable{
    private String userId;
    private String userName;
    private String userPicture;
    private ArrayList<Friend> friends;


    protected Friend(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        userPicture = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public ArrayList<Friend> getFriends() {
        return friends;
    }
    public Friend(){

    }

    public Friend(String userId, String userName, String userPicture) {
        this.userId = userId;
        this.userName = userName;
        this.userPicture = userPicture;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(userPicture);
    }
}
