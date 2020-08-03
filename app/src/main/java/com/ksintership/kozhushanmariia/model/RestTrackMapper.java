package com.ksintership.kozhushanmariia.model;

import com.ksintership.kozhushanmariia.contract.mappers.RestModelMapper;

import java.util.ArrayList;
import java.util.List;

public class RestTrackMapper implements RestModelMapper<TrackModelRest, TrackModel> {
    @Override
    public TrackModel restToModel(TrackModelRest restModel) {
        return new TrackModel(restModel.id,
                restModel.trackName,
                restModel.album.title,
                restModel.artist.name,
                restModel.album.coverMedium,
                restModel.album.coverBig,
                restModel.preview);
    }

    @Override
    public List<TrackModel> restToModel(List<TrackModelRest> listRestModel) {
        List<TrackModel> result = new ArrayList<>();
        for (TrackModelRest restModel : listRestModel) {
            result.add(restToModel(restModel));
        }
        return result;
    }
}
