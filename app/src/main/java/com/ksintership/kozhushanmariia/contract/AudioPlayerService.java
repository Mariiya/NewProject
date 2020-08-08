package com.ksintership.kozhushanmariia.contract;

import androidx.annotation.Nullable;

import com.ksintership.kozhushanmariia.model.TrackModel;

import java.util.List;

public interface AudioPlayerService {
    void setListener(Listener listener);

    void setTrackQueue(List<TrackModel> queue);

    void setTrack(TrackModel track, boolean autoPrepare);

    @Nullable
    TrackModel getCurrentTrack();

    boolean isPlaying();

    /**
     * @return true even if track is not prepared
     * but it will start play if preparation will be successful
     */
    boolean isShouldPlaying();

    /**
     * @return true if track prepared and it will immediately start play
     * false if track not prepared and it will loading and if successful call onTrackPrepared
     */
    boolean play();

    void pause();

    void nextTrack();

    void previousTrack();

    int getTrackDuration();

    int getCurrentPosition();

    void seekTo(int milliseconds);

    void setLooping(boolean looping);

    boolean isPreparedTrack();

    interface Listener {
        default void onStartPreparation() { }

        void onTrackPrepared();

        default void onPrepareFailed() { }

        void onTrackChanged(TrackModel currentTrack);
    }
}
