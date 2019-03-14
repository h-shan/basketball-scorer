package com.shan.howard.balltracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shan.howard.balltracker.dao.TeamDao;
import com.shan.howard.balltracker.datamodels.Team;

@Database(entities = {Team.class}, version = 1)
public abstract class BallTrackerDatabase extends RoomDatabase {
    public abstract TeamDao teamDao();

    private static volatile BallTrackerDatabase INSTANCE;

    public static BallTrackerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BallTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BallTrackerDatabase.class, "ball_tracker_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}