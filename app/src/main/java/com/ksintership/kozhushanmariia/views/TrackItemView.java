package com.ksintership.kozhushanmariia.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.BindableTrackItem;
import com.ksintership.kozhushanmariia.utils.ViewUtil;

public class TrackItemView extends ConstraintLayout implements BindableTrackItem {

    private ImageView albumCover;
    private TextView trackName;
    private TextView artistName;

    public TrackItemView(Context context) {
        super(context);
    }

    public TrackItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrackItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        albumCover = findViewById(R.id.album_cover);
        trackName = findViewById(R.id.track_name);
        artistName = findViewById(R.id.artist_name);
    }

    @Override
    public void bind(String trackName, String artistName, String albumCoverUrl) {
        this.trackName.setText(trackName);
        this.artistName.setText(artistName);
        ViewUtil.loadImage(albumCover, albumCoverUrl, R.drawable.ic_audiotrack_24);
    }
}
