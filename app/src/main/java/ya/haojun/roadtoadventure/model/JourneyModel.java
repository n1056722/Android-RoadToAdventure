package ya.haojun.roadtoadventure.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asus on 2017/4/6.
 */

public class JourneyModel implements Parcelable{
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

    protected JourneyModel(Parcel in) {
        journeyId = in.readInt();
        journeyName = in.readString();
        journeyContent = in.readString();
        startTime = in.readString();
        stopTime = in.readString();
        count = in.readInt();
    }

    public static final Creator<JourneyModel> CREATOR = new Creator<JourneyModel>() {
        @Override
        public JourneyModel createFromParcel(Parcel in) {
            return new JourneyModel(in);
        }

        @Override
        public JourneyModel[] newArray(int size) {
            return new JourneyModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(journeyId);
        dest.writeString(journeyName);
        dest.writeString(journeyContent);
        dest.writeString(startTime);
        dest.writeString(stopTime);
        dest.writeInt(count);
    }
}
