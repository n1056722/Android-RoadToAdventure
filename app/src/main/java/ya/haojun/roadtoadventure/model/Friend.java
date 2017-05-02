package ya.haojun.roadtoadventure.model;


public class Friend {
    private String userID;
    private String userName;
    private String userPicture;

    public Friend(String userID, String userName, String userPicture) {
        this.userID = userID;
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
