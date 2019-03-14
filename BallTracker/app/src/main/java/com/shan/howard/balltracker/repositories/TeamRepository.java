package com.shan.howard.balltracker.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.shan.howard.balltracker.BallTrackerDatabase;
import com.shan.howard.balltracker.dao.TeamDao;
import com.shan.howard.balltracker.datamodels.Team;

import java.util.List;

public class TeamRepository {

    private TeamDao mTeamDao;
    private LiveData<List<Team>> mAllTeams;

    public TeamRepository(Application application) {
        BallTrackerDatabase db = BallTrackerDatabase.getDatabase(application);
        mTeamDao = db.teamDao();
        mAllTeams = mTeamDao.selectAll();
    }

    public LiveData<List<Team>> getAllTeams() {
        return mAllTeams;
    }

    public void insert(Team... aTeams) {
        new insertAsyncTask(mTeamDao).execute(aTeams);
    }

    public void update(Team... params) {
        new updateAsyncTask(mTeamDao).execute(params);
    }

    public void delete(Team... params) {
        new deleteAsyncTask(mTeamDao).execute(params);
    }

    private static class insertAsyncTask extends AsyncTask<Team, Void, Void> {

        private TeamDao mAsyncTaskDao;

        insertAsyncTask(TeamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Team... params) {

            mAsyncTaskDao.insert(params);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Team, Void, Void> {

        private TeamDao mAsyncTaskDao;

        updateAsyncTask(TeamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Team... params) {
            mAsyncTaskDao.update(params);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Team, Void, Void> {

        private TeamDao mAsyncTaskDao;

        deleteAsyncTask(TeamDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Team... params) {
            mAsyncTaskDao.delete(params);
            return null;
        }
    }
}
