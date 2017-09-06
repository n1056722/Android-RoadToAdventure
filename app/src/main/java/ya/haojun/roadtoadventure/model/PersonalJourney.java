package ya.haojun.roadtoadventure.model;


import java.util.ArrayList;

public class PersonalJourney extends CommonModel {
    private int personalJourneyId;
    private String userId;
    private String name;
    private String content;
    private String picture;
    private String points;
    private String status;
    private String isOpen;
    private String startTime;
    private String endTime;
    private String createDate;
    private String modifyDate;
   private ArrayList<PersonalJourney> personalJourneys;

    public ArrayList<PersonalJourney> getPersonalJourneys() {
        return personalJourneys;
    }

    public int getPersonalJourneyId() {
        return personalJourneyId;
    }

    public void setPersonalJourneyId(int personalJourneyId) {
        this.personalJourneyId = personalJourneyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public PersonalJourney(int personalJourneyId, String userId, String name, String content, String picture, String points, String status, String isOpen, String startTime, String endTime, String createDate, String modifyDate) {
        this.personalJourneyId = personalJourneyId;
        this.userId = userId;
        this.name = name;
        this.content = content;
        this.picture = picture;
        this.points = points;
        this.status = status;
        this.isOpen = isOpen;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.modifyDate = modifyDate;


    }
    public PersonalJourney(){

    }

}
