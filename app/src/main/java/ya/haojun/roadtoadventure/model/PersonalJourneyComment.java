package ya.haojun.roadtoadventure.model;

import java.util.ArrayList;

/**
 * Created by rin84 on 2017/9/30.
 */

public class PersonalJourneyComment extends CommonModel{
    private String userId;
    private String userName;
    private String userPicture;
    private String comment;
    private String createDate;
    //
    private int personalJourneyId;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getPersonalJourneyId() {
        return personalJourneyId;
    }

    public void setPersonalJourneyId(int personalJourneyId) {
        this.personalJourneyId = personalJourneyId;
    }
}
