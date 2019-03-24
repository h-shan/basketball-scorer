package com.shan.howard.balltracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shan.howard.balltracker.datamodels.Team;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Team... aTeam);

    @Update
    public void update(Team... teams);

    @Query("SELECT * FROM teams WHERE deleted_at_ IS NULL")
    LiveData<List<Team>> selectAllLive();

    @Query("SELECT * FROM teams WHERE id_ = :anId")
    LiveData<Team> selectById(long anId);

    @Query("UPDATE teams SET deleted_at_ = CAST(strftime('%s', 'now') As BIGINT) WHERE id_ IN (:teamIds)")
    public int delete(List<Long> teamIds);
}