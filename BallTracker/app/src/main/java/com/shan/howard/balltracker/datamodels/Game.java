package com.shan.howard.balltracker.datamodels;

import java.util.Date;
import java.util.List;

public class Game {
    private String id;
    private Date date;
    private String yourTeamId;
    private String opponentTeamId;
    private String location;
    private int yourTeamScore;
    private int opposingTeamScore;
    private List<String> q1EventIds;
    private List<String> q2EventIds;
    private List<String> q3EventIds;
    private List<String> q4EventIds;
    private List<String> overtimeEventIds;

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getYourTeamId() {
        return yourTeamId;
    }

    public void setYourTeamId(String yourTeamId) {
        this.yourTeamId = yourTeamId;
    }

    public String getOpponentTeamId() {
        return opponentTeamId;
    }

    public void setOpponentTeamId(String opponentTeamId) {
        this.opponentTeamId = opponentTeamId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYourTeamScore() {
        return yourTeamScore;
    }

    public void setYourTeamScore(int yourTeamScore) {
        this.yourTeamScore = yourTeamScore;
    }

    public int getOpposingTeamScore() {
        return opposingTeamScore;
    }

    public void setOpposingTeamScore(int opposingTeamScore) {
        this.opposingTeamScore = opposingTeamScore;
    }

    public List<String> getQ1EventIds() {
        return q1EventIds;
    }

    public void setQ1EventIds(List<String> q1EventIds) {
        this.q1EventIds = q1EventIds;
    }

    public List<String> getQ2EventIds() {
        return q2EventIds;
    }

    public void setQ2EventIds(List<String> q2EventIds) {
        this.q2EventIds = q2EventIds;
    }

    public List<String> getQ3EventIds() {
        return q3EventIds;
    }

    public void setQ3EventIds(List<String> q3EventIds) {
        this.q3EventIds = q3EventIds;
    }

    public List<String> getQ4EventIds() {
        return q4EventIds;
    }

    public void setQ4EventIds(List<String> q4EventIds) {
        this.q4EventIds = q4EventIds;
    }

    public List<String> getOvertimeEventIds() {
        return overtimeEventIds;
    }

    public void setOvertimeEventIds(List<String> overtimeEventIds) {
        this.overtimeEventIds = overtimeEventIds;
    }
}
