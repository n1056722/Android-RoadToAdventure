package ya.haojun.roadtoadventure.model;

import java.util.ArrayList;

/**
 * Created by rin84 on 2017/9/24.
 */

public class GroupChat extends CommonModel{
    private int groupChatId;
    private int groupId;
    private String userId;
    private String userName;
    private String userPicture;
    private String content;
    private String createDate;
    //
    private ArrayList<GroupChat> groupChats;
    private int lastChatId;

    public int getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
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

    public int getLastChatId() {
        return lastChatId;
    }

    public void setLastChatId(int lastChatId) {
        this.lastChatId = lastChatId;
    }

    public ArrayList<GroupChat> getGroupChats() {
        return groupChats;
    }

    @Override
    public String toString() {
        return "GroupChat{" +
                "groupChatId=" + groupChatId +
                ", groupId=" + groupId +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
