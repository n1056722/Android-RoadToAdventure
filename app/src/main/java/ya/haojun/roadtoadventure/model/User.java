package ya.haojun.roadtoadventure.model;


public class User {
    private static final User ourInstance = new User();
    private String userID;
    private String userName;
    private String userPicture;
    public static User getInstance() {
        return ourInstance;
    }

    private User() {

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
