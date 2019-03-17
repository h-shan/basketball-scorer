package com.shan.howard.balltracker.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.shan.howard.balltracker.datamodels.Event;
import com.shan.howard.balltracker.repositories.EventRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository mRepository;

    private LiveData<List<Event>> mAllEvents;

    public EventViewModel (Application application) {
        super(application);
        mRepository = new EventRepository(application);
        mAllEvents = mRepository.selectAllLive();
    }

    public LiveData<List<Event>> selectAllLive() {
        return mAllEvents;
    }

    public void selectById(long anId) {
        mRepository.selectById(anId);
    }

    public void insert(Event... aEvents) { mRepository.insert(aEvents); }

    public void update(Event... aEvents) {
        mRepository.update(aEvents);
    }

    public void delete(Event... aEvents) {
        mRepository.delete(aEvents);
    }
}