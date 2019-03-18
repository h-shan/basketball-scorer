package com.shan.howard.balltracker.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.shan.howard.balltracker.BallTrackerDatabase;
import com.shan.howard.balltracker.dao.PlayerDao;
import com.shan.howard.balltracker.datamodels.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerRepository {

    private PlayerDao mPlayerDao;
    private LiveData<List<Player>> mAllPlayers;

    public PlayerRepository(Application application) {
        BallTrackerDatabase db = BallTrackerDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
        mAllPlayers = mPlayerDao.selectAllLive();
    }

    public LiveData<List<Player>> selectAllLive() {
        return mAllPlayers;
    }

    public Player selectById(long anId) {
        return mPlayerDao.selectById(anId);
    }

    public void insert(Player... aPlayers) {
        new insertAsyncTask(mPlayerDao).execute(aPlayers);
    }

    public void update(Player... params) {
        new updateAsyncTask(mPlayerDao).execute(params);
    }

    public void delete(Player... params) {
        new deleteAsyncTask(mPlayerDao).execute(params);
    }

    private static class insertAsyncTask extends AsyncTask<Player, Void, Void> {

        private PlayerDao mAsyncTaskDao;

        insertAsyncTask(PlayerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Player... params) {

            mAsyncTaskDao.insert(params);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Player, Void, Void> {

        private PlayerDao mAsyncTaskDao;

        updateAsyncTask(PlayerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Player... params) {
            mAsyncTaskDao.update(params);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Player, Void, Void> {

        private PlayerDao mAsyncTaskDao;

        deleteAsyncTask(PlayerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Player... params) {
            mAsyncTaskDao.delete(Arrays.stream(params).map(Player::getId).collect(Collectors.toList()));
            return null;
        }
    }
}
