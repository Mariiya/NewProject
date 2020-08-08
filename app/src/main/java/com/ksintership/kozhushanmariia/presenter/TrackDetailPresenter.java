package com.ksintership.kozhushanmariia.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.repository.TrackRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class TrackDetailPresenter implements TrackDetailContract.Presenter {

    @Inject
    TrackRepository repository;

    private ExecutorService executor;

    private MutableLiveData<TrackModel> trackModelLd;

    private TrackDetailContract.View view;

    public TrackDetailPresenter() {
        AppInjector.getAppComponent().inject(this);
        trackModelLd = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void attach(TrackDetailContract.View view) {
        this.view = view;
    }

    public void loadTrackModel(long trackId) {
        executor.execute(() -> {
            TrackModel trackModel = repository.findTrack(trackId);
            if (trackModel != null) {
                trackModelLd.postValue(trackModel);
            }
        });
    }

    @Override
    public LiveData<TrackModel> getTrackModelLd() {
        return trackModelLd;
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void clear() {
        executor.shutdownNow();
        executor = null;
        trackModelLd = null;
    }
}
