package ya.haojun.roadtoadventure.model;


public class GroupMember {
    private String userID;
    private String userName;
    private String userPicture;

    public GroupMember(String userName, String userPicture) {
        this.userName = userName;
        this.userPicture = userPicture;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
}
