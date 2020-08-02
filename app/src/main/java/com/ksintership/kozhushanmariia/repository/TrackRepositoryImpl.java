package com.ksintership.kozhushanmariia.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.ksintership.kozhushanmariia.contract.ApiOnFailureCallback;
import com.ksintership.kozhushanmariia.model.RestTrackToTrackMapper;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.rest.DeezerResponse;
import com.ksintership.kozhushanmariia.rest.RestApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class TrackRepositoryImpl implements TrackRepository {
    private RestTrackToTrackMapper mapper;

    private RestApiService restApiService;
    private List<TrackModel> trackList;

    private int totalCountSearchedList;
    private String linkToNext;

    public TrackRepositoryImpl(RestApiService restApiService) {
        this.restApiService = restApiService;
        mapper = new RestTrackToTrackMapper();
        trackList = new ArrayList<>();
    }

    @WorkerThread
    @Nullable
    @Override
    public List<TrackModel> searchTracks(String query, ApiOnFailureCallback callback) {
        try {
            Response<DeezerResponse> response = restApiService.search(query).execute();
            if (response.isSuccessful() || response.body() != null) {
                linkToNext = response.body().getLinkToNext();
                totalCountSearchedList = response.body().getTotal();
                trackList.clear();
                trackList.addAll(mapper.restModelToInternalModel(response.body().getTrackList()));
                return trackList;
            } else if (response.errorBody() != null) {
                Log.e(RestApiService.LOG_TAG, response.errorBody().string());
                callback.onFailure(response.errorBody().string());
            }
        } catch (IOException e) {
            Log.e(RestApiService.LOG_TAG, e.getMessage());
        }
        return null;
    }

    @Nullable
    @Override
    public List<TrackModel> getCachedTracks() {
        return trackList;
    }

    @WorkerThread
    @Nullable
    @Override
    public List<TrackModel> reloadTrackList() {
        if (linkToNext == null) return null;
        try {
            Response<DeezerResponse> response = restApiService.reloadTrackList(linkToNext).execute();
            if (response.isSuccessful() || response.body() != null) {
                linkToNext = response.body().getLinkToNext();
                trackList.addAll(mapper.restModelToInternalModel(response.body().getTrackList()));
                return trackList;
            } else if (response.errorBody() != null) {
                Log.e(RestApiService.LOG_TAG, response.errorBody().string());
            }
        } catch (IOException e) {
            Log.e(RestApiService.LOG_TAG, e.getMessage());
        }
        return null;
    }

    @Nullable
    @Override
    public TrackModel findTrack(long trackId) {
        if (trackList.isEmpty()) return null;
        for (TrackModel track : trackList) {
            if (track.getId() == trackId)
                return track;
        }
        return null;
    }
}
