package com.shan.howard.balltracker.datamodels;

import java.util.List;
import java.util.UUID;

public class Team {
    private String id = UUID.randomUUID().toString();
    private String name = "New team";
    private int color = 0xFF0000;
    private String coachName = "";
    private List<String> playerIds;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public List<String> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<String> playerIds) {
        this.playerIds = playerIds;
    }
}
