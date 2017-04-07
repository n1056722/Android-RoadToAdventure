package ya.haojun.roadtoadventure.model;

/**
 * Created by asus on 2017/4/6.
 */

public class JourneyModel {
    private int journeyId;
    private String journeyName;
    private String journeyContent;
    private String startTime;
    private String stopTime;
    private int count;

    public JourneyModel(){

    }

    public JourneyModel(String startTime, String stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public String getJourneyContent() {
        return journeyContent;
    }

    public void setJourneyContent(String journeyContent) {
        this.journeyContent = journeyContent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
