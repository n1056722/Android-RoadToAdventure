package ya.haojun.roadtoadventure.model;


import java.util.ArrayList;

public class PersonalJourney extends CommonModel {
    private int personalJourneyId;
    private String userId;
    private String name;
    private String content;
    private String points;
    private String status;
    private String isOpen;
    private String startTime;
    private String endTime;
    private String createDate;
    private String modifyDate;
    private ArrayList<String> pictures;
    private ArrayList<LocationRecordModel> routes;
    //
    private ArrayList<PersonalJourney> personalJourneys;

    public PersonalJourney() {
        pictures = new ArrayList<>();
        personalJourneys = new ArrayList<>();
        routes= new ArrayList<>();
    }

    public ArrayList<PersonalJourney> getPersonalJourneys() {
        return personalJourneys;
    }

    public ArrayList<String> getPictures() {
        return pictures;
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

    public ArrayList<LocationRecordModel> getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "PersonalJourney{" +
                "personalJourneyId=" + personalJourneyId +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", points='" + points + '\'' +
                ", status='" + status + '\'' +
                ", isOpen='" + isOpen + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", createDate='" + createDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", pictures=" + pictures +
                '}';
    }
}
