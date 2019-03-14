package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

enum EventType {
    TWO_POINTERS, THREE_POINTERS, FREE_THROWS, FOULS
}

@Entity(tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_")
    private String id;

    @ColumnInfo(name = "game_id_")
    private String gameId;

    @ColumnInfo(name = "player_id_")
    private String playerId;

    @ColumnInfo(name = "team_id_")
    private String teamId;

    @ColumnInfo(name = "event_type_")
    private EventType eventType;

    @ColumnInfo(name = "quarter_")
    int quarter;

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

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }
}
