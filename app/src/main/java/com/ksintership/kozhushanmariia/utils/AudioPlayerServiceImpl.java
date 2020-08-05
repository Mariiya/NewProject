package com.ksintership.kozhushanmariia.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ksintership.kozhushanmariia.contract.AudioPlayerService;
import com.ksintership.kozhushanmariia.model.TrackModel;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AudioPlayerServiceImpl implements AudioPlayerService {

    private static final String TAG = AudioPlayerServiceImpl.class.getSimpleName();

    @Nullable
    private AudioPlayerService.Listener listener;

    private MediaPlayer mediaPlayer;
    private Context context;

    private List<TrackModel> queue;
    private TrackModel currentTrackInQueue;

    private boolean isShouldPlaying;
    private boolean isPreparedTrack;
    private boolean isLoading;

    public AudioPlayerServiceImpl(Context context) {
        this.context = context;
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener((mp -> {
            isPreparedTrack = true;
            isLoading = false;
            if (listener != null) {
                listener.onTrackPrepared();
            }
            if (isShouldPlaying) {
                mp.start();
            }
        }));
        mediaPlayer.setOnCompletionListener(mp -> nextTrack());
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "media player error: " + what + "; " + extra);
            isLoading = false;
            if (listener != null) {
                listener.onPrepareFailed();
            }
            return true;
        });
    }

    private void prepareTrack() {
        try {
            if (listener != null) {
                listener.onStartPreparation();
            }
            isPreparedTrack = false;
            isLoading = true;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.parse(currentTrackInQueue.getTrackPreviewUrl()));
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(PreferencesManager.getRepeatTrack() == RepeatTrackPref.REPEAT_ONE);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            isLoading = false;
            if (listener != null) {
                listener.onPrepareFailed();
            }
        }
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void setTrackQueue(List<TrackModel> queue) {
        this.queue = queue;
    }

    @Override
    public void setTrack(TrackModel track, boolean autoPrepare) {
        currentTrackInQueue = track;
        isPreparedTrack = false;
        if (autoPrepare || isShouldPlaying) {
            prepareTrack();
        }
        if (listener != null) {
            listener.onTrackChanged(track);
        }
    }

    @Override
    @Nullable
    public TrackModel getCurrentTrack() {
        return currentTrackInQueue;
    }

    @Override
    public boolean isPlaying() {
        if (!isPreparedTrack) return false;
        else return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isShouldPlaying() {
        return isShouldPlaying;
    }

    @Override
    public boolean play() {
        if (currentTrackInQueue == null)
            new AssertionError("Track is not set. Initially call setTrack");
        isShouldPlaying = true;
        if (isLoading) return false;
        if (isPreparedTrack) {
            mediaPlayer.start();
            return true;
        } else {
            prepareTrack();
            return false;
        }
    }

    @Override
    public void pause() {
        if (currentTrackInQueue == null)
            new AssertionError("Track is not set. Initially call setTrack");
        isShouldPlaying = false;
        if (isPreparedTrack && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void nextTrack() {
        if (currentTrackInQueue == null)
            new AssertionError("Track is not set. Initially call setTrack");
        if (queue == null)
            new AssertionError("Queue is not set. Initially call setQueue");
        int nextTrack;
        if (PreferencesManager.hasShufflePlay()) {
            nextTrack = getRandomForShuffle();
            if (nextTrack == queue.indexOf(currentTrackInQueue)) {
                nextTrack = getRandomForShuffle();
            }
        } else {
            nextTrack = queue.indexOf(currentTrackInQueue) + 1;
        }
        isShouldPlaying = true;
        if (queue.size() == nextTrack) {
            isShouldPlaying = PreferencesManager.getRepeatTrack() == RepeatTrackPref.REPEAT_QUEUE;
            nextTrack = 0;
        }
        if (listener != null && nextTrack == queue.size() - 1) {
            listener.onEndQueue();
        }
        currentTrackInQueue = queue.get(nextTrack);
        if (listener != null) {
            listener.onTrackChanged(currentTrackInQueue);
        }
        prepareTrack();
    }

    @Override
    public void previousTrack() {
        if (currentTrackInQueue == null)
            new AssertionError("Track is not set. Initially call setTrack");
        if (queue == null)
            new AssertionError("Queue is not set. Initially call setQueue");
        int previousTrack = queue.indexOf(currentTrackInQueue) - 1;
        if (previousTrack < 0) {
            previousTrack = queue.size() - 1;
        }
        currentTrackInQueue = queue.get(previousTrack);
        if (listener != null) {
            listener.onTrackChanged(currentTrackInQueue);
        }
        prepareTrack();
    }

    @Override
    public int getTrackDuration() {
        if (isPreparedTrack) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (isPreparedTrack) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(int milliseconds) {
        if (currentTrackInQueue == null)
            new AssertionError("Track is not set. Initially call setTrack");
        if (isPreparedTrack) {
            mediaPlayer.seekTo(milliseconds);
            if (!isShouldPlaying) {
                mediaPlayer.start();
                isShouldPlaying = true;
            }
        }
    }

    @Override
    public void setLooping(boolean looping) {
        mediaPlayer.setLooping(looping);
    }

    @Override
    public boolean isPreparedTrack() {
        return isPreparedTrack;
    }

    private int getRandomForShuffle() {
        return new Random().nextInt(queue.size() - 1);
    }
}
