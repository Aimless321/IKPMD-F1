package eu.aimless.f1predictor.repository.model;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class Race {
    private String date;
    private String raceName;
    private String round;
    private String season;
    private String time;
    private String url;

    public Race() {
    }

    public Race(String date, String raceName, String round, String season, String time, String url) {
        this.date = date;
        this.raceName = raceName;
        this.round = round;
        this.season = season;
        this.time = time;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
