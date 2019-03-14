package com.shan.howard.balltracker.datamodels;

public class Event {
    public enum EventType {
        TWO_POINTERS, THREE_POINTERS, FREE_THROWS, FOULS
    }

    private String id;
    private String gameId;
    private String playerId;
    private String teamId;
    private EventType eventType;
    int quarterNumber;

    public String getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getQuarterNumber() {
        return quarterNumber;
    }

    public void setQuarterNumber(int quarterNumber) {
        this.quarterNumber = quarterNumber;
    }
}
