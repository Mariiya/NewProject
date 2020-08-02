package com.ksintership.kozhushanmariia.model;

import com.ksintership.kozhushanmariia.contract.mappers.RestModelMapper;

public class RestTrackToTrackMapper implements RestModelMapper<TrackModelRest, TrackModel> {
    @Override
    public TrackModel restModelToInternalModel(TrackModelRest restModel) {
        return new TrackModel(restModel.id,
                restModel.trackName,
                restModel.album.title,
                restModel.artist.name,
                restModel.album.coverMedium,
                restModel.album.coverBig,
                restModel.preview);
    }
}
