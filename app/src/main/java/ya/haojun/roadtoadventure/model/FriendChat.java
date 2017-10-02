package ya.haojun.roadtoadventure.model;


import java.util.ArrayList;

public class FriendChat extends CommonModel {
    private int chatID;
    private String userId;
    private String userName;
    private String userPicture;
    private String friendId;
    private String friendName;
    private String friendPicture;
    private String content;
    private String createDate;
    //
    private int lastChatId;
    private ArrayList<FriendChat> chats;

    public FriendChat() {
        chats = new ArrayList<>();
    }

    public String getFriendID() {
        return friendId;
    }

    public void setFriendID(String friendId) {
        this.friendId = friendId;
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
        return userId;
    }

    public void setUserID(String userId) {
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

    public int getLastChatId() {
        return lastChatId;
    }

    public void setLastChatId(int lastChatId) {
        this.lastChatId = lastChatId;
    }

    public ArrayList<FriendChat> getChats() {
        return chats;
    }
}
