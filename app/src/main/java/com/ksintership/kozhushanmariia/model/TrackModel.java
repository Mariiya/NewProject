package com.ksintership.kozhushanmariia.model;

import androidx.annotation.Nullable;

public class TrackModel {
    private final long deezerId;

    private final String trackName;
    private final String albumName;
    private final String artistName;

    private final String albumCoverMediumUrl;
    private final String albumCoverBigUrl;
    private final String trackPreviewUrl;

    public TrackModel(long deezerId,
                      String trackName,
                      String albumName,
                      String artistName,
                      String albumCoverMediumUrl,
                      String albumCoverBigUrl,
                      String trackPreviewUrl) {
        this.deezerId = deezerId;
        this.trackName = trackName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.albumCoverMediumUrl = albumCoverMediumUrl;
        this.albumCoverBigUrl = albumCoverBigUrl;
        this.trackPreviewUrl = trackPreviewUrl;
    }

    public long getId() {
        return deezerId;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumCoverMediumUrl() {
        return albumCoverMediumUrl;
    }

    public String getAlbumCoverBigUrl() {
        return albumCoverBigUrl;
    }

    public String getTrackPreviewUrl() {
        return trackPreviewUrl;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof TrackModel) {
            return ((TrackModel) obj).getId() == this.getId();
        } else return false;
    }

    @Override
    public int hashCode() {
        return (int) deezerId;
    }
}
