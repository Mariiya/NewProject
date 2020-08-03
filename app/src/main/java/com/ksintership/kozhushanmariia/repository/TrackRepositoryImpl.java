package com.ksintership.kozhushanmariia.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.ksintership.kozhushanmariia.contract.ApiOnFailureCallback;
import com.ksintership.kozhushanmariia.database.TrackDao;
import com.ksintership.kozhushanmariia.model.EntityTrack;
import com.ksintership.kozhushanmariia.model.EntityTrackMapper;
import com.ksintership.kozhushanmariia.model.RestTrackMapper;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.rest.DeezerResponse;
import com.ksintership.kozhushanmariia.rest.RestApiService;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class TrackRepositoryImpl implements TrackRepository {
    private RestTrackMapper restMapper;
    private EntityTrackMapper entityMapper;

    private ExecutorService executor;

    private RestApiService restApiService;
    private TrackDao trackDao;
    private List<TrackModel> trackList;

    private String linkToNext;

    public TrackRepositoryImpl(RestApiService restApiService, TrackDao trackDao) {
        this.restApiService = restApiService;
        this.trackDao = trackDao;
        executor = Executors.newSingleThreadExecutor();
        restMapper = new RestTrackMapper();
        entityMapper = new EntityTrackMapper();
        trackList = new ArrayList<>();
    }

    @WorkerThread
    @Nullable
    @Override
    public List<TrackModel> searchTracks(String query, ApiOnFailureCallback callback) {
        try {
            Response<DeezerResponse> response = restApiService.search(query).execute();
            if (response.isSuccessful() && response.body() != null && response.body().getTotal() != 0) {
                linkToNext = response.body().getLinkToNext();
                trackList.clear();
                trackList.addAll(restMapper.restToModel(response.body().getTrackList()));
                cacheListIfNeed();
                return trackList;
            } else if (response.errorBody() != null) {
                Log.e(RestApiService.LOG_TAG, response.errorBody().string());
                callback.onFailure(response.errorBody().string());
            }
        } catch (IOException e) {
            Log.e(RestApiService.LOG_TAG, e.getMessage());
            callback.onFailure(e.getMessage());
        }
        return null;
    }

    @Nullable
    @Override
    public List<TrackModel> getCachedTracks() {
        if (trackList.isEmpty()) {
            List<EntityTrack> entityTracks = trackDao.getAll();
            if (entityTracks != null) {
                trackList = entityMapper.entityToModel(trackDao.getAll());
            }
        }
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
                trackList.addAll(restMapper.restToModel(response.body().getTrackList()));
                cacheListIfNeed();
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

    private void cacheListIfNeed() {
        if (PreferencesManager.hasSaveLastSearch()) {
            executor.execute(() -> {
                trackDao.clear();
                trackDao.insertAll(entityMapper.modelToEntity(trackList));
            });
        }
    }

}
