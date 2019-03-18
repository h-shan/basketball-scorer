package com.shan.howard.balltracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.shan.howard.balltracker.datamodels.Game;
import com.shan.howard.balltracker.repositories.GameRepository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private GameRepository mRepository;

    private LiveData<List<Game>> mAllGames;

    public GameViewModel (Application application) {
        super(application);
        mRepository = new GameRepository(application);
        mAllGames = mRepository.selectAllLive();
    }

    public LiveData<List<Game>> selectAllLive() {
        return mAllGames;
    }

    public void selectById(long anId) {
        mRepository.selectById(anId);
    }

    public void insert(Game... aGames) { mRepository.insert(aGames); }

    public void update(Game... aGames) {
        mRepository.update(aGames);
    }

    public void delete(Game... aGames) {
        mRepository.delete(aGames);
    }
}