package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;

@Entity(tableName = "events")
public class Event implements Comparable<Event> {

    @Ignore
    public static final String THREE_POINTER = "THREE_POINTER";
    public static final String TWO_POINTER = "TWO_POINTER";
    public static final String FREE_THROW = "FREE_THROW";
    public static final String FOUL = "FOUL";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_")
    private long id;

    @ColumnInfo(name = "game_id_")
    private long gameId;

    @ColumnInfo(name = "team_id_")
    private long teamId;

    @ColumnInfo(name = "event_type_")
    private String eventType;

    @ColumnInfo(name = "quarter_")
    private int quarter;

    @ColumnInfo(name = "deleted_at_")
    private Calendar deletedAt = null;

    @ColumnInfo(name = "created_at_")
    private Calendar createdAt = Calendar.getInstance();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public Calendar getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Calendar deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int compareTo(@NonNull Event o) {
        if (this.quarter != o.quarter) {
            return this.quarter - o.quarter;
        } else {
            return this.createdAt.compareTo(o.createdAt);
        }
    }
}
