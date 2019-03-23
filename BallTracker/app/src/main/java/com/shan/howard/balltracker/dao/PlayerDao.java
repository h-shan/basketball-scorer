package com.shan.howard.balltracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shan.howard.balltracker.datamodels.Player;

import java.util.List;

@Dao
public interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player... aPlayer);

    @Update
    public void update(Player... players);

    @Query("SELECT * FROM players WHERE deleted_at_ IS NULL")
    LiveData<List<Player>> selectAllLive();

    @Query("SELECT * FROM players WHERE id_ = :anId")
    Player selectById(long anId);

    @Query("UPDATE games SET deleted_at_ = strftime('%s', 'now') WHERE id_ IN(:playerIds)")
    public void delete(List<Long> playerIds);
}