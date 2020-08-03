package com.ksintership.kozhushanmariia.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.repository.TrackRepository;

import java.util.concurrent.Executors;

import javax.inject.Inject;

public class TrackDetailViewModel extends ViewModel {
    @Inject
    TrackRepository repository;

    private MutableLiveData<TrackModel> trackModelLd = new MutableLiveData<>();

    public void init(long trackId) {
        AppInjector.getAppComponent().inject(this);
        Executors.newSingleThreadExecutor().execute(() -> {
            TrackModel trackModel = repository.findTrack(trackId);
            if (trackModel != null) {
                trackModelLd.postValue(trackModel);
            }
        });
    }

    public LiveData<TrackModel> getTrackModelLd() {
        return trackModelLd;
    }
}
