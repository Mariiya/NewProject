package com.ksintership.kozhushanmariia.model;

import com.ksintership.kozhushanmariia.contract.mappers.EntityModelMapper;

import java.util.ArrayList;
import java.util.List;

public class EntityTrackMapper implements EntityModelMapper<EntityTrack, TrackModel> {
    @Override
    public TrackModel entityToModel(EntityTrack entityModel) {
        return new TrackModel(entityModel.deezerId,
                entityModel.trackName,
                entityModel.albumName,
                entityModel.artistName,
                entityModel.albumCoverMedium,
                entityModel.albumCoverBig,
                entityModel.preview);
    }

    @Override
    public List<TrackModel> entityToModel(List<EntityTrack> entityModel) {
        ArrayList<TrackModel> result = new ArrayList<>();
        for (EntityTrack entity : entityModel) {
            result.add(entityToModel(entity));
        }
        return result;
    }

    @Override
    public EntityTrack modelToEntity(TrackModel internalModel) {
        EntityTrack result = new EntityTrack();
        result.deezerId = internalModel.getId();
        result.trackName = internalModel.getTrackName();
        result.albumName = internalModel.getAlbumName();
        result.artistName = internalModel.getArtistName();
        result.albumCoverMedium = internalModel.getAlbumCoverMediumUrl();
        result.albumCoverBig = internalModel.getAlbumCoverBigUrl();
        result.preview = internalModel.getTrackPreviewUrl();
        return result;
    }

    @Override
    public List<EntityTrack> modelToEntity(List<TrackModel> internalModel) {
        ArrayList<EntityTrack> result = new ArrayList<>();
        for (TrackModel model : internalModel) {
            result.add(modelToEntity(model));
        }
        return result;
    }
}
