package ya.haojun.roadtoadventure.model;


public class Chat {
    private String userID;
    private String userName;
    private String userPicture;
    private String content;
    private String createDate;

    public Chat(String userID, String userName, String userPicture, String content, String createDate) {
        this.userID = userID;
        this.userName = userName;
        this.userPicture = userPicture;
        this.content = content;
        this.createDate = createDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
