package com.shan.howard.balltracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.shan.howard.balltracker.datamodels.Team;
import com.shan.howard.balltracker.repositories.TeamRepository;

import java.util.List;

public class TeamViewModel extends AndroidViewModel {

    private TeamRepository mRepository;

    private LiveData<List<Team>> mAllTeams;

    public TeamViewModel (Application application) {
        super(application);
        mRepository = new TeamRepository(application);
        mAllTeams = mRepository.getAllTeams();
    }

    public LiveData<List<Team>> getAllTeams() { return mAllTeams; }

    public void insert(Team... aTeams) { mRepository.insert(aTeams); }

    public void update(Team... aTeams) {
        mRepository.update(aTeams);
    }

    public void delete(Team... aTeams) {
        mRepository.delete(aTeams);
    }
}