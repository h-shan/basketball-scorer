package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

@Entity(tableName = "games")
public class Game implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_")
    private long id;

    @ColumnInfo(name = "date_")
    private Calendar date = Calendar.getInstance();

    @ColumnInfo(name = "your_team_id_")
    private long yourTeamId;

    @ColumnInfo(name = "opposing_team_id_")
    private long opposingTeamId;

    @ColumnInfo(name = "notes_")
    private String notes;

    @ColumnInfo(name = "your_team_score_")
    private int yourTeamScore;

    @ColumnInfo(name = "opposing_team_score_")
    private int opposingTeamScore;

    @ColumnInfo(name = "deleted_at_")
    private Calendar deletedAt = null;

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

    public long getYourTeamId() {
        return yourTeamId;
    }

    public void setYourTeamId(long yourTeamId) {
        this.yourTeamId = yourTeamId;
    }

    public long getOpposingTeamId() {
        return opposingTeamId;
    }

    public void setOpposingTeamId(long opposingTeamId) {
        this.opposingTeamId = opposingTeamId;
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

    public Calendar getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Calendar deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeSerializable(this.date);
        dest.writeLong(this.yourTeamId);
        dest.writeLong(this.opposingTeamId);
        dest.writeString(this.notes);
        dest.writeInt(this.yourTeamScore);
        dest.writeInt(this.opposingTeamScore);
        dest.writeSerializable(this.deletedAt);
    }

    public Game() {
    }

    protected Game(Parcel in) {
        this.id = in.readLong();
        this.date = (Calendar) in.readSerializable();
        this.yourTeamId = in.readLong();
        this.opposingTeamId = in.readLong();
        this.notes = in.readString();
        this.yourTeamScore = in.readInt();
        this.opposingTeamScore = in.readInt();
        this.deletedAt = (Calendar) in.readSerializable();
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
