package ya.haojun.roadtoadventure.model;


import java.util.ArrayList;

public class Group extends CommonModel{
    private int groupId;
    private String userId;
    private String name;
    private String picturePath;
    private ArrayList<GroupMember> members;
    //
    private ArrayList<Group> groups;

    public Group(){
        members = new ArrayList<>();
        groups = new ArrayList<>();
    }

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
}
