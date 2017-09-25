package ya.haojun.roadtoadventure.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Group extends CommonModel implements Parcelable{
    private int groupId;
    private String name;
    private String picturePath;
    private ArrayList<GroupMember> members;
    //
    private String userId;
    private ArrayList<Group> groups;

    public Group(){
        members = new ArrayList<>();
        groups = new ArrayList<>();
    }

    protected Group(Parcel in) {
        groupId = in.readInt();
        name = in.readString();
        picturePath = in.readString();
        members = in.createTypedArrayList(GroupMember.CREATOR);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public ArrayList<GroupMember> getMembers() {
        return members;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groupId);
        dest.writeString(name);
        dest.writeString(picturePath);
        dest.writeTypedList(members);
    }
}
