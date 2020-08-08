package com.ksintership.kozhushanmariia.repository;


import com.ksintership.kozhushanmariia.model.TrackModel;

public interface TrackPreviewRepository {

    /**
     * Loads track preview mp3 to local storage for {@param trackModel}
     */
    void loadTrackPreview(TrackModel trackModel, Callback callback);

    int getCacheSizeInMb();

    void clearCache();

    interface Callback {
        void onLoaded(TrackModel trackModel);

        void onFailed();
    }

}
