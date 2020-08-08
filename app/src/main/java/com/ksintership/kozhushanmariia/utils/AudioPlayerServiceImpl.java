package com.ksintership.kozhushanmariia.utils;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ksintership.kozhushanmariia.contract.AudioPlayerService;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.repository.TrackPreviewRepository;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class AudioPlayerServiceImpl implements AudioPlayerService {

    private static final String TAG = AudioPlayerServiceImpl.class.getSimpleName();

    @Nullable
    private AudioPlayerService.Listener listener;

    private TrackPreviewRepository repository;

    private MediaPlayer mediaPlayer;

    private List<TrackModel> queue;
    private TrackModel currentTrackInQueue;

    private Stack<Integer> backStack = new Stack<>();

    private boolean isShouldPlaying;
    private boolean isPreparedTrack;
    private boolean isLoading;

    public AudioPlayerServiceImpl(TrackPreviewRepository repository) {
        this.repository = repository;
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
        if (currentTrackInQueue.hasLocalPathPreview()) {
            setTrackInPlayer(currentTrackInQueue);
        } else {
            if (listener != null) {
                listener.onStartPreparation();
            }
            repository.loadTrackPreview(currentTrackInQueue, new TrackPreviewRepository.Callback() {
                @Override
                public void onLoaded(TrackModel trackModel) {
                    if (currentTrackInQueue.equals(trackModel)) {
                        currentTrackInQueue.setLocalPathPreview(trackModel.getLocalPathPreview());
                        setTrackInPlayer(currentTrackInQueue);
                    }
                }

                @Override
                public void onFailed() {
                    if (listener != null) {
                        listener.onPrepareFailed();
                    }
                }
            });
        }
    }

    private void setTrackInPlayer(TrackModel track) {
        try {
            isPreparedTrack = false;
            isLoading = true;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(track.getLocalPathPreview());
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(PreferencesManager.getRepeatTrack() == RepeatTrackPref.REPEAT_ONE);
        } catch (IOException e) {
            if (track.hasLocalPathPreview()) {
                track.setLocalPathPreview(null);
                prepareTrack();
                return;
            }
            Log.e(TAG, "setTrackInPlayer exception: " + e.getMessage());
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
    public void setTrackQueue(@NonNull List<TrackModel> queue) {
        this.queue = queue;
        backStack.clear();
    }

    @Override
    public void setTrack(@NonNull TrackModel track, boolean autoPrepare) {
        backStack.clear();
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

        int nextTrackIndex;
        int currentTrackIndex = queue.indexOf(currentTrackInQueue);
        backStack.push(currentTrackIndex);

        if (PreferencesManager.hasShufflePlay()) {
            nextTrackIndex = getRandomForShuffle();
            if (nextTrackIndex == currentTrackIndex) {
                nextTrackIndex = getRandomForShuffle();
            }
        } else {
            nextTrackIndex = currentTrackIndex + 1;
        }
        isShouldPlaying = true;
        if (queue.size() == nextTrackIndex) {
            isShouldPlaying = PreferencesManager.getRepeatTrack() == RepeatTrackPref.REPEAT_QUEUE;
            nextTrackIndex = 0;
        }
        currentTrackInQueue = queue.get(nextTrackIndex);
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

        int previousTrack = backStack.isEmpty() ? queue.indexOf(currentTrackInQueue) - 1 : backStack.pop();
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
