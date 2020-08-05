package com.ksintership.kozhushanmariia.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.AudioPlayerService;
import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.utils.ViewUtil;

import javax.inject.Inject;

public class FloatingAudioPlayerView extends ConstraintLayout implements AudioPlayerService.Listener {

    @Inject
    AudioPlayerService audioPlayerService;

    private ImageView albumCover;
    private TextView trackName;
    private ImageButton playButton;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        AppInjector.getAppComponent().inject(this);

        albumCover = findViewById(R.id.album_cover);
        trackName = findViewById(R.id.track_name_p);
        playButton = findViewById(R.id.play_button);

        playButton.setOnClickListener(v -> {
            if (!audioPlayerService.isPlaying()) {
                if (audioPlayerService.play()) {
                    playButton.setImageResource(R.drawable.ic_pause_24);
                }
            } else {
                audioPlayerService.pause();
                playButton.setImageResource(R.drawable.ic_play_arrow_24);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        audioPlayerService.setListener(this);
        bind(audioPlayerService.getCurrentTrack());
    }

    public void bind(TrackModel trackModel) {
        if (trackModel == null) return;
        ViewUtil.loadImage(albumCover, trackModel.getAlbumCoverMediumUrl(), null);
        String trackNameStr = trackModel.getTrackName() + " - " + trackModel.getArtistName();
        trackName.setText(trackNameStr);

        if (audioPlayerService.isPlaying())
            playButton.setImageResource(R.drawable.ic_pause_24);
    }

    @Override
    public void onTrackPrepared() {
        playButton.setImageResource(R.drawable.ic_pause_24);
    }

    @Override
    public void onTrackChanged(TrackModel currentTrack) {
        bind(currentTrack);
    }

    public FloatingAudioPlayerView(Context context) {
        super(context);
    }

    public FloatingAudioPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingAudioPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
