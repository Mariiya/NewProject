package com.ksintership.kozhushanmariia.presenter;

import androidx.lifecycle.LiveData;

import com.ksintership.kozhushanmariia.model.TrackModel;

public interface TrackDetailContract {

    interface View extends AbstractView {

    }

    interface Presenter extends com.ksintership.kozhushanmariia.presenter.Presenter<View> {
        void loadTrackModel(long trackId);

        LiveData<TrackModel> getTrackModelLd();
    }
}
