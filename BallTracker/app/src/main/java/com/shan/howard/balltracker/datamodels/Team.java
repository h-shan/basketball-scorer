package com.shan.howard.balltracker.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.shan.howard.balltracker.R;

@Entity(tableName = "teams")
public class Team implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_")
    private long id;

    @ColumnInfo(name = "name_")
    private String name = "New team";

    @ColumnInfo(name = "color_")
    private int color = 0xFFFF0000;

    @ColumnInfo(name = "coach_")
    private String coach = "";

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.color);
        dest.writeString(this.coach);
    }

    public Team() {
    }

    protected Team(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.color = in.readInt();
        this.coach = in.readString();
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
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
