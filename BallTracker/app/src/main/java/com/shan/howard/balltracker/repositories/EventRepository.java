package com.shan.howard.balltracker.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.shan.howard.balltracker.BallTrackerDatabase;
import com.shan.howard.balltracker.dao.EventDao;
import com.shan.howard.balltracker.datamodels.Event;
import com.shan.howard.balltracker.datamodels.Game;

import java.util.List;

public class EventRepository {

    private EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;

    public EventRepository(Application application) {
        BallTrackerDatabase db = BallTrackerDatabase.getDatabase(application);
        mEventDao = db.eventDao();
        mAllEvents = mEventDao.selectAllLive();
    }

    public LiveData<List<Event>> selectAllLive() {
        return mAllEvents;
    }

    public LiveData<List<Event>> selectByGameId(Long gameId) {
        return mEventDao.selectByGameId(gameId);
    }

    public Event selectById(long anId) {
        return mEventDao.selectById(anId);
    }

    public void insert(Event... aEvents) {
        new insertAsyncTask(mEventDao).execute(aEvents);
    }

    public void update(Event... params) {
        new updateAsyncTask(mEventDao).execute(params);
    }

    public void delete(Event... params) {
        new deleteAsyncTask(mEventDao).execute(params);
    }

    private static class insertAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mAsyncTaskDao;

        insertAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Event... params) {

            mAsyncTaskDao.insert(params);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mAsyncTaskDao;

        updateAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            mAsyncTaskDao.update(params);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mAsyncTaskDao;

        deleteAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            mAsyncTaskDao.delete(params);
            return null;
        }
    }
}
