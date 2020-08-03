package com.ksintership.kozhushanmariia.repository;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.ksintership.kozhushanmariia.contract.ApiOnFailureCallback;
import com.ksintership.kozhushanmariia.model.TrackModel;

import java.util.List;

public interface TrackRepository {

    @WorkerThread
    @Nullable
    List<TrackModel> searchTracks(String query, ApiOnFailureCallback callback);

    @WorkerThread
    @Nullable
    List<TrackModel> getCachedTracks();

    @Nullable
    TrackModel findTrack(long trackId);

    @WorkerThread
    @Nullable
    List<TrackModel> reloadTrackList();
}
