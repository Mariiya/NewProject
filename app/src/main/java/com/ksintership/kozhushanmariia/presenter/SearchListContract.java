package com.ksintership.kozhushanmariia.presenter;

import androidx.lifecycle.LiveData;

import com.ksintership.kozhushanmariia.model.TrackModel;

import java.util.List;

public interface SearchListContract {
    interface View extends AbstractView {
        void onSearchError();
    }

    interface Presenter extends com.ksintership.kozhushanmariia.presenter.Presenter<View> {
        void search(String query);

        void reloadTrackList();

        void loadCachedData();

        LiveData<List<TrackModel>> getTrackListLd();
    }
}
