package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "games")
public class Game {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_")
    private long id;

    @ColumnInfo(name = "date_")
    private Calendar date = Calendar.getInstance();

    @ColumnInfo(name = "your_team_id_")
    private String yourTeamId;

    @ColumnInfo(name = "opponent_team_id_")
    private String opponentTeamId;

    @ColumnInfo(name = "notes_")
    private String notes;

    @ColumnInfo(name = "your_team_score_")
    private int yourTeamScore;

    @ColumnInfo(name = "opposing_team_score_")
    private int opposingTeamScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
}
