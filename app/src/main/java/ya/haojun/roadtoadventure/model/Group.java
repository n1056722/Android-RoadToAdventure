package ya.haojun.roadtoadventure.model;


public class Group {
    private int groupID;
    private String groupName;
    private String groupPicture;

    public Group(String groupName, String groupPicture) {
        this.groupName = groupName;
        this.groupPicture = groupPicture;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPicture() {
        return groupPicture;
    }

    public void setGroupPicture(String groupPicture) {
        this.groupPicture = groupPicture;
    }
}
