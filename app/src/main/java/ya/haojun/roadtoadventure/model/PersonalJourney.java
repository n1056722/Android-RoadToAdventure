package ya.haojun.roadtoadventure.model;


public class PersonalJourney {
    private int personalJourneyID;
    private String userID;
    private String personalJourneyName;
    private String personalJourneyContent;
    private String picture;
    private String points;
    private String status;
    private String isOpen;
    private String startTime;
    private String endTime;
    private String createDate;
    private String modifyDate;

    public PersonalJourney(String name, String content, String status, String picture, String createDate) {
        this.personalJourneyName = name;
        this.personalJourneyContent = content;
        this.picture = picture;
        this.status = status;
        this.createDate = createDate;
    }

    public int getPersonalJourneyID() {
        return personalJourneyID;
    }

    public void setPersonalJourneyID(int personalJourneyID) {
        this.personalJourneyID = personalJourneyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPersonalJourneyName() {
        return personalJourneyName;
    }

    public void setPersonalJourneyName(String personalJourneyName) {
        this.personalJourneyName = personalJourneyName;
    }

    public String getPersonalJourneyContent() {
        return personalJourneyContent;
    }

    public void setPersonalJourneyContent(String personalJourneyContent) {
        this.personalJourneyContent = personalJourneyContent;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
}
