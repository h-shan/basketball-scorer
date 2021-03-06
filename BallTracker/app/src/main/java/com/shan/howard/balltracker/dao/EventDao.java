package com.shan.howard.balltracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shan.howard.balltracker.datamodels.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event... aEvents);

    @Update
    public void update(Event... events);

    @Query("SELECT * from events")
    LiveData<List<Event>> selectAllLive();

    @Query("SELECT * FROM events WHERE id_ = :anId")
    LiveData<Event> selectById(long anId);

    @Query("SELECT * FROM events WHERE game_id_ = :gameId")
    LiveData<List<Event>> selectByGameId(Long gameId);

    @Query("SELECT * FROM events WHERE game_id_ = :gameId AND team_id_ = :teamId")
    LiveData<List<Event>> selectByGameAndTeamId(long gameId, long teamId);

    @Delete
    public void delete(Event... events);
}
