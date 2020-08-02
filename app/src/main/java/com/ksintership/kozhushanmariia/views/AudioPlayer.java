package com.ksintership.kozhushanmariia.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.ShowingInformationFragment;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;

import java.io.IOException;

public class AudioPlayer extends ConstraintLayout {
    private final static String TAG = AudioPlayer.class.getSimpleName();

    private final static long SCHEDULE_DELAY_UPDATE_SEEK = 250L;

    private MediaPlayer mediaPlayer;

    private String trackUrl = "https://cdns-preview-4.dzcdn.net/stream/c-4d8be70fe82515043eb8ab04272291ef-6.mp3";

    private ShowingInformationFragment showingFragment;

    private SeekBar seekBar;
    private ImageButton playButton;
    private ImageButton repeatButton;
    private TextView currentTimeView;
    private TextView trackTimeView;

    private boolean isLoading = false;
    private boolean isInitializeMediaPlayer = false;

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(mp -> {
            seekBar.setMax(mp.getDuration());
            setTime(trackTimeView, mp.getDuration());
            mp.start();
            playButton.setImageResource(R.drawable.ic_pause_circle_filled_24);
            startUpdatingSeekBar();
            mp.setLooping(PreferencesManager.hasRepeatTrack());
            isLoading = false;
            isInitializeMediaPlayer = true;
            showingFragment.hideProgressBar();
        });

        mediaPlayer.setOnCompletionListener((mp) -> {
            stopUpdatingSeekBar();
            playButton.setImageResource(R.drawable.ic_play_circle_filled_24);
            seekBar.setProgress(0);
            setTime(currentTimeView, 0);
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            showingFragment.showSnackbar(R.string.audio_player_error);
            isLoading = false;
            isInitializeMediaPlayer = false;
            showingFragment.hideProgressBar();
            return false;
        });
    }

    private void initTrack() {
        if (mediaPlayer == null)
            initMediaPlayer();
        try {
            showingFragment.showProgressBar();
            isLoading = true;
            mediaPlayer.setDataSource(getContext(), Uri.parse(trackUrl));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            showingFragment.showSnackbar(R.string.audio_player_error);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        seekBar = findViewById(R.id.seek_bar);
        playButton = findViewById(R.id.play_button);
        repeatButton = findViewById(R.id.repeat_button);
        currentTimeView = findViewById(R.id.current_time);
        trackTimeView = findViewById(R.id.track_time);

        initMediaPlayer();

        if (PreferencesManager.hasRepeatTrack()) {
            repeatButton.setImageResource(R.drawable.ic_repeat_one_24);
        }

        playButton.setOnClickListener((view) -> {
            if (mediaPlayer == null) {
                initTrack();
                return;
            }
            if (isLoading) return;
            if (!mediaPlayer.isPlaying()) {
                if (!isInitializeMediaPlayer) {
                    initTrack();
                    return;
                }
                mediaPlayer.start();
                playButton.setImageResource(R.drawable.ic_pause_circle_filled_24);
                startUpdatingSeekBar();
            } else {
                mediaPlayer.pause();
                stopUpdatingSeekBar();
                playButton.setImageResource(R.drawable.ic_play_circle_filled_24);
            }
        });

        repeatButton.setOnClickListener((view) -> {
            boolean hasRepeat = !PreferencesManager.hasRepeatTrack();
            PreferencesManager.setRepeatTrack(hasRepeat);
            repeatButton.setImageResource(hasRepeat ? R.drawable.ic_repeat_one_24 : R.drawable.ic_repeat_24);
            mediaPlayer.setLooping(hasRepeat);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    setTime(currentTimeView, i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdatingSeekBar();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startUpdatingSeekBar();
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }


    final Handler updateSeekBarHandler = new Handler();
    final Runnable updatingSeekBar = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            seekBar.setProgress(mediaPlayer.getCurrentPosition(), true);
            setTime(currentTimeView, mediaPlayer.getCurrentPosition());
            updateSeekBarHandler.postDelayed(this, SCHEDULE_DELAY_UPDATE_SEEK);
        }
    };

    private void startUpdatingSeekBar() {
        updateSeekBarHandler.postDelayed(updatingSeekBar, SCHEDULE_DELAY_UPDATE_SEEK);
    }

    private void stopUpdatingSeekBar() {
        updateSeekBarHandler.removeCallbacks(updatingSeekBar);
    }

    private void setTime(TextView textView, long timeMillis) {
        if (timeMillis == 0) {
            textView.setText("0:00");
            return;
        }
        int min = (int) ((timeMillis / 1000) / 60);
        int sec = (int) ((timeMillis / 1000) % 60);
        String text = min + ":" + (sec > 9 ? sec : "0" + sec);
        textView.setText(text);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isLoading) showingFragment.hideProgressBar();
        stopUpdatingSeekBar();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void setShowingInformationFragment(ShowingInformationFragment showingFragment) {
        this.showingFragment = showingFragment;
    }

    public AudioPlayer(Context context) {
        super(context);
    }

    public AudioPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
