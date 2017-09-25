package ya.haojun.roadtoadventure.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GroupMember extends CommonModel implements Parcelable{
    private String userId;
    private String userName;
    private String userPicture;
    private String groupRoleId;
    private String groupRoleName;
    //
    private ArrayList<GroupMember> members;
    private int groupId;
    private String targetUserId;

    public GroupMember(){
        members = new ArrayList<>();
    }

    public GroupMember(String userId, String userName, String userPicture) {
        this.userId = userId;
        this.userName = userName;
        this.userPicture = userPicture;
    }

    protected GroupMember(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        userPicture = in.readString();
        groupRoleId = in.readString();
        groupRoleName = in.readString();
    }

    public static final Creator<GroupMember> CREATOR = new Creator<GroupMember>() {
        @Override
        public GroupMember createFromParcel(Parcel in) {
            return new GroupMember(in);
        }

        @Override
        public GroupMember[] newArray(int size) {
            return new GroupMember[size];
        }
    };

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

    public String getGroupRoleId() {
        return groupRoleId;
    }

    public void setGroupRoleId(String groupRoleId) {
        this.groupRoleId = groupRoleId;
    }

    public String getGroupRoleName() {
        return groupRoleName;
    }

    public void setGroupRoleName(String groupRoleName) {
        this.groupRoleName = groupRoleName;
    }

    public ArrayList<GroupMember> getMembers() {
        return members;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
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
        dest.writeString(groupRoleId);
        dest.writeString(groupRoleName);
    }
}
