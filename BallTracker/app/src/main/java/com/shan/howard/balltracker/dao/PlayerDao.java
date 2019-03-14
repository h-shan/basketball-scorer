package com.shan.howard.balltracker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shan.howard.balltracker.datamodels.Player;
import com.shan.howard.balltracker.datamodels.Team;

import java.util.List;

public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Player... aPlayers);

    @Update
    public void update(Player... aPlayers);

    @Query("SELECT * from players")
    LiveData<List<Player>> selectAll();

    @Delete
    public void delete(Player... aPlayers);
}
