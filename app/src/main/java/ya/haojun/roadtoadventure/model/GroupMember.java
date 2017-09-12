package ya.haojun.roadtoadventure.model;


public class GroupMember {
    private String userId;
    private String userName;
    private String userPicture;

    public GroupMember(String userId, String userName, String userPicture) {
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
}
