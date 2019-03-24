package com.shan.howard.balltracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.shan.howard.balltracker.datamodels.Player;
import com.shan.howard.balltracker.repositories.PlayerRepository;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository mRepository;

    private LiveData<List<Player>> mAllPlayers;

    public PlayerViewModel (Application application) {
        super(application);
        mRepository = new PlayerRepository(application);
        mAllPlayers = mRepository.selectAllLive();
    }

    public LiveData<List<Player>> selectAllLive() {
        return mAllPlayers;
    }

    public LiveData<List<Player>> selectByTeamId(long teamId) {
        return mRepository.selectByTeam(teamId);
    }

    public LiveData<Player> selectById(long anId) {
        return mRepository.selectById(anId);
    }

    public void insert(Player... aPlayers) { mRepository.insert(aPlayers); }

    public void update(Player... aPlayers) {
        mRepository.update(aPlayers);
    }

    public void delete(Player... aPlayers) {
        mRepository.delete(aPlayers);
    }

}