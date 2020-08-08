package com.ksintership.kozhushanmariia.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.AudioPlayerService;
import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;
import com.ksintership.kozhushanmariia.utils.RepeatTrackPref;

import javax.inject.Inject;


public class AudioPlayerView extends ConstraintLayout implements AudioPlayerService.Listener {
    private final static String TAG = AudioPlayerView.class.getSimpleName();

    private final static long SCHEDULE_DELAY_UPDATE_SEEK = 250L;

    @Inject
    AudioPlayerService audioPlayerService;

    private String trackUrl;

    private SeekBar seekBar;
    private TextView currentTimeView;
    private TextView trackTimeView;

    private ImageButton playButton;
    private ImageButton nextButton;
    private ImageButton previousButton;

    private ImageButton shuffleButton;
    private ImageButton repeatButton;

    private boolean isAvailableNextButton = true;

    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    @Override
    public void onTrackPrepared() {
        changeViewToPlay();
        prepareSeek();
    }

    private void changeViewToPlay() {
        playButton.setImageResource(R.drawable.ic_pause_circle_filled_24);
        startUpdatingSeekBar();
    }

    @Override
    public void onTrackChanged(TrackModel currentTrack) {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        AppInjector.getAppComponent().inject(this);

        seekBar = findViewById(R.id.seek_bar);
        currentTimeView = findViewById(R.id.current_time);
        trackTimeView = findViewById(R.id.track_time);

        playButton = findViewById(R.id.play_button);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);

        repeatButton = findViewById(R.id.repeat_button);
        shuffleButton = findViewById(R.id.shuffle_button);


        setRepeatImg(PreferencesManager.getRepeatTrack());
        setShuffleImg(PreferencesManager.hasShufflePlay());

        if (audioPlayerService.isPreparedTrack()) {
            prepareSeek();
        }

        if (audioPlayerService.isPlaying()) {
            changeViewToPlay();
        }

        playButton.setOnClickListener(v -> {
            if (!audioPlayerService.isPlaying()) {
                if (audioPlayerService.play()) {
                    playButton.setImageResource(R.drawable.ic_pause_circle_filled_24);
                    startUpdatingSeekBar();
                }
            } else {
                stopUpdatingSeekBar();
                audioPlayerService.pause();
                playButton.setImageResource(R.drawable.ic_play_circle_filled_72);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (!isAvailableNextButton) return;
            stopUpdatingSeekBar();
            audioPlayerService.nextTrack();
            prepareSeek();
        });

        previousButton.setOnClickListener(v -> {
            stopUpdatingSeekBar();
            audioPlayerService.previousTrack();
            prepareSeek();
        });

        repeatButton.setOnClickListener((view) -> {
            RepeatTrackPref nextPref = PreferencesManager.getRepeatTrack().nextPref();
            PreferencesManager.setRepeatTrack(nextPref);
            audioPlayerService.setLooping(nextPref == RepeatTrackPref.REPEAT_ONE);
            setRepeatImg(nextPref);
        });

        shuffleButton.setOnClickListener(v -> {
            boolean nextPref = !PreferencesManager.hasShufflePlay();
            PreferencesManager.setShufflePlay(nextPref);
            setShuffleImg(PreferencesManager.hasShufflePlay());
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
                audioPlayerService.seekTo(seekBar.getProgress());
            }
        });

    }

    private void prepareSeek() {
        seekBar.setMax(audioPlayerService.getTrackDuration());
        seekBar.setProgress(audioPlayerService.getCurrentPosition());
        setTime(trackTimeView, audioPlayerService.getTrackDuration());
        setTime(currentTimeView, audioPlayerService.getCurrentPosition());
    }

    private void setRepeatImg(RepeatTrackPref pref) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_repeat_24, getContext().getTheme());
        switch (pref) {
            case NOT_REPEAT:
                drawable.setTint(Color.GRAY);
                repeatButton.setImageDrawable(drawable);
                break;
            case REPEAT_QUEUE:
                drawable.setTint(Color.WHITE);
                repeatButton.setImageDrawable(drawable);
                break;
            case REPEAT_ONE:
                repeatButton.setImageResource(R.drawable.ic_repeat_one_24);
        }
    }

    void setShuffleImg(boolean isShuffle) {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_shuffle_24, getContext().getTheme());
        if (isShuffle) {
            drawable.setTint(Color.WHITE);
        } else {
            drawable.setTint(Color.GRAY);
        }
        shuffleButton.setImageDrawable(drawable);
    }

    final Handler updateSeekBarHandler = new Handler();
    final Runnable updatingSeekBar = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            seekBar.setProgress(audioPlayerService.getCurrentPosition(), true);
            setTime(currentTimeView, audioPlayerService.getCurrentPosition());
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


    public AudioPlayerView(Context context) {
        super(context);
    }

    public AudioPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
