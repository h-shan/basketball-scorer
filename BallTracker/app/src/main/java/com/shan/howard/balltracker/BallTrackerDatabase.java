package com.shan.howard.balltracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.shan.howard.balltracker.dao.EventDao;
import com.shan.howard.balltracker.dao.GameDao;
import com.shan.howard.balltracker.dao.TeamDao;
import com.shan.howard.balltracker.datamodels.Converter;
import com.shan.howard.balltracker.datamodels.Event;
import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.datamodels.Team;

@Database(entities = {Team.class, Event.class, Game.class}, version = 7, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class BallTrackerDatabase extends RoomDatabase {
    public abstract TeamDao teamDao();
    public abstract GameDao gameDao();
    public abstract EventDao eventDao();

    private static volatile BallTrackerDatabase INSTANCE;

    public static BallTrackerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BallTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BallTrackerDatabase.class, "ball_tracker_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}