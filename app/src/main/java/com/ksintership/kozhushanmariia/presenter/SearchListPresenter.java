package com.ksintership.kozhushanmariia.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.model.SearchHistoryModel;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.repository.SearchHistoryRepository;
import com.ksintership.kozhushanmariia.repository.TrackRepository;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class SearchListPresenter implements SearchListContract.Presenter {

    @Inject
    TrackRepository trackRepository;

    @Inject
    SearchHistoryRepository historyRepository;

    private ExecutorService executor;

    private SearchListContract.View view;

    private MutableLiveData<List<TrackModel>> trackList;

    private String lastQuery = "";

    public SearchListPresenter() {
        AppInjector.getAppComponent().inject(this);

        executor = Executors.newSingleThreadExecutor();

        trackList = new MutableLiveData<>();
    }

    @Override
    public void attach(SearchListContract.View view) {
        this.view = view;
    }

    public void search(String query) {
        if (query.equals(lastQuery)) return;
        view.showProgressBar();
        lastQuery = query;
        executor.execute(() -> {
            trackList.postValue(trackRepository.searchTracks(query, (msg) -> {
                view.onSearchError();
                lastQuery = null;
            }));
            if (view != null) view.hideProgressBar();
        });
        historyRepository.add(new SearchHistoryModel(query, System.currentTimeMillis()));
    }

    public void reloadTrackList() {
        view.showProgressBar();
        executor.execute(() -> {
            List<TrackModel> reloadedTrackList = trackRepository.reloadTrackList();
            if (reloadedTrackList != null) {
                trackList.postValue(reloadedTrackList);
            }
            if (view != null) view.hideProgressBar();
        });
    }

    @Override
    public void loadCachedData() {
        if (PreferencesManager.hasSaveLastSearch()) {
            executor.execute(() -> {
                trackList.postValue(trackRepository.getCachedTracks());
            });
        }
    }

    public LiveData<List<TrackModel>> getTrackListLd() {
        return trackList;
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void clear() {
        executor.shutdownNow();
        executor = null;
        trackList = null;
    }

}
