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
}
