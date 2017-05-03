package ya.haojun.roadtoadventure.model;


public class GooglePolyLine {
    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "GooglePolyLine{" +
                "points='" + points + '\'' +
                '}';
    }
}
