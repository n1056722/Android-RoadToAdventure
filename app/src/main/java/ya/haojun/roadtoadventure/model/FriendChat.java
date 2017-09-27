package ya.haojun.roadtoadventure.model;


public class FriendChat {
    private int chatID;
    private String userID;
    private String userName;
    private String userPicture;
    private String friendID;
    private String friendName;
    private String friendPicture;
    private String content;
    private String createDate;



    public FriendChat(String userID, String userName, String userPicture, String content, String createDate , int chatID , String friendID, String friendName, String friendPicture) {
        this.userID = userID;
        this.userName = userName;
        this.userPicture = userPicture;
        this.content = content;
        this.createDate = createDate;
        this.chatID = chatID;
        this.friendID = friendID;
        this.friendName = friendName;
        this.friendPicture = friendPicture;

    }

    public FriendChat() {
        
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendPicture() {
        return friendPicture;
    }

    public void setFriendPicture(String friendPicture) {
        this.friendPicture = friendPicture;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
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


    public void getCreateDate(String string) {
    }
}
