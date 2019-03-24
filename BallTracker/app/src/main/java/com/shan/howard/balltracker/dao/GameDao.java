package com.shan.howard.balltracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shan.howard.balltracker.datamodels.Game;

import java.util.List;

@Dao
public interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Game... aGame);

    @Update
    public void update(Game... games);

    @Query("SELECT * FROM games WHERE deleted_at_ IS NULL")
    LiveData<List<Game>> selectAllLive();

    @Query("SELECT * FROM games WHERE id_ = :anId")
    LiveData<Game> selectById(long anId);

    @Query("UPDATE games SET deleted_at_ = strftime('%s', 'now') WHERE id_ IN(:gameIds)")
    public void delete(List<Long> gameIds);
}