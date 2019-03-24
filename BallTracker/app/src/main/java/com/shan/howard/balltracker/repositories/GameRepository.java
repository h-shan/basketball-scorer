package com.shan.howard.balltracker.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.shan.howard.balltracker.BallTrackerDatabase;
import com.shan.howard.balltracker.dao.GameDao;
import com.shan.howard.balltracker.datamodels.Game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameRepository {

    private GameDao mGameDao;
    private LiveData<List<Game>> mAllGames;

    public GameRepository(Application application) {
        BallTrackerDatabase db = BallTrackerDatabase.getDatabase(application);
        mGameDao = db.gameDao();
        mAllGames = mGameDao.selectAllLive();
    }

    public LiveData<List<Game>> selectAllLive() {
        return mAllGames;
    }

    public LiveData<Game> selectById(long anId) {
        return mGameDao.selectById(anId);
    }

    public void insert(Game... aGames) {
        new insertAsyncTask(mGameDao).execute(aGames);
    }

    public void update(Game... params) {
        new updateAsyncTask(mGameDao).execute(params);
    }

    public void delete(Game... params) {
        new deleteAsyncTask(mGameDao).execute(params);
    }

    private static class insertAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        insertAsyncTask(GameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Game... params) {

            mAsyncTaskDao.insert(params);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        updateAsyncTask(GameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Game... params) {
            mAsyncTaskDao.update(params);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        deleteAsyncTask(GameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Game... params) {
            mAsyncTaskDao.delete(Arrays.stream(params).map(Game::getId).collect(Collectors.toList()));
            return null;
        }
    }
}
