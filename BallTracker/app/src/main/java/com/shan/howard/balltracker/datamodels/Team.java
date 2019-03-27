package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.UUID;

@Entity(tableName = "teams")
public class Team implements Parcelable {
    @PrimaryKey()
    @ColumnInfo(name = "id_")
    private Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

    @ColumnInfo(name = "name_")
    private String name = "New team";

    @ColumnInfo(name = "color_")
    private int color = 0xFFFF0000;

    @ColumnInfo(name = "coach_")
    private String coach = "";

    @ColumnInfo(name = "deleted_at_")
    private Calendar deletedAt = null;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
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
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.color);
        dest.writeString(this.coach);
        dest.writeSerializable(this.deletedAt);
    }

    public Team() {
    }

    protected Team(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.color = in.readInt();
        this.coach = in.readString();
        this.deletedAt = (Calendar) in.readSerializable();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
