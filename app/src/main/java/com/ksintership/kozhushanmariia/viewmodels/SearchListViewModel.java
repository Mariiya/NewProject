package com.ksintership.kozhushanmariia.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ksintership.kozhushanmariia.contract.ShowingInformationFragment;
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

public class SearchListViewModel extends ViewModel {

    @Inject
    TrackRepository trackRepository;

    @Inject
    SearchHistoryRepository historyRepository;

    private ExecutorService executor;

    private ShowingInformationFragment showingInfoFragment;

    private MutableLiveData<List<TrackModel>> trackList;
    private MutableLiveData<Boolean> showErrorMessage;

    private String lastQuery = "";

    public void init(ShowingInformationFragment showingInfoFragment) {
        this.showingInfoFragment = showingInfoFragment;

        AppInjector.getAppComponent().inject(this);

        executor = Executors.newSingleThreadExecutor();

        trackList = new MutableLiveData<>();
        showErrorMessage = new MutableLiveData<>(false);
    }

    public void loadCachedTrack() {
        if (PreferencesManager.hasSaveLastSearch()) {
            executor.execute(() -> {
                trackList.postValue(trackRepository.getCachedTracks());
            });
        }
    }

    public void search(String query) {
        if (query.equals(lastQuery)) return;
        showingInfoFragment.showProgressBar();
        lastQuery = query;
        executor.execute(() -> {
            trackList.postValue(trackRepository.searchTracks(query, (msg) -> {
                showErrorMessage.postValue(true);
                lastQuery = null;
            }));
            showingInfoFragment.hideProgressBar();
        });
        historyRepository.add(new SearchHistoryModel(query, System.currentTimeMillis()));
    }

    public void reloadTrackList() {
        showingInfoFragment.showProgressBar();
        executor.execute(() -> {
            List<TrackModel> reloadedTrackList = trackRepository.reloadTrackList();
            if (reloadedTrackList != null) {
                trackList.postValue(reloadedTrackList);
            }
            showingInfoFragment.hideProgressBar();
        });
    }

    public LiveData<List<TrackModel>> getTrackListLd() {
        return trackList;
    }

    public LiveData<Boolean> getShowErrorMessageLd() {
        return showErrorMessage;
    }


}
