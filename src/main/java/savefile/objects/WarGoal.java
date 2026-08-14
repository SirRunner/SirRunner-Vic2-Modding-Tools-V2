package savefile.objects;

import java.time.LocalDate;

public class WarGoal {
    private int stateProvinceID;
    private String casusBelli;
    private String country;
    private String actor;
    private String receiver;
    private double score;
    private double change;
    private LocalDate date;
    private boolean isFulfilled;

    public int getStateProvinceID() {
        return stateProvinceID;
    }

    public void setStateProvinceID(int stateProvinceID) {
        this.stateProvinceID = stateProvinceID;
    }

    public String getCasusBelli() {
        return casusBelli;
    }

    public void setCasusBelli(String casusBelli) {
        this.casusBelli = casusBelli;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isFulfilled() {
        return isFulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        isFulfilled = fulfilled;
    }
}
