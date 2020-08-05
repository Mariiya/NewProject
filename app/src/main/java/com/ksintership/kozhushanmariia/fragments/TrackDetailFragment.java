package com.ksintership.kozhushanmariia.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.contract.AudioPlayerService;
import com.ksintership.kozhushanmariia.contract.ShowingInformationFragment;
import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.repository.TrackRepository;
import com.ksintership.kozhushanmariia.utils.Constants;
import com.ksintership.kozhushanmariia.utils.ViewUtil;
import com.ksintership.kozhushanmariia.viewmodels.TrackDetailViewModel;
import com.ksintership.kozhushanmariia.views.AudioPlayerView;

import javax.inject.Inject;

public class TrackDetailFragment extends BaseFragment<TrackDetailViewModel> implements AudioPlayerService.Listener {

    @Inject
    TrackRepository trackRepository;
    @Inject
    AudioPlayerService audioPlayerService;

    private ShowingInformationFragment informationFragment;

    private ImageView albumCover;
    private TextView trackName;
    private TextView artistPlusAlbumName;

    private AudioPlayerView audioPlayer;

    @Override
    protected void initViews() {
        ((BaseActivity) getActivity()).initToolbar("Track information", true);
        bindTrackInfo(audioPlayerService.getCurrentTrack());
    }

    @Override
    protected void findViews(View root) {
        albumCover = root.findViewById(R.id.album_cover);
        trackName = root.findViewById(R.id.track_name);
        artistPlusAlbumName = root.findViewById(R.id.album_plus_artist_name);
        audioPlayer = root.findViewById(R.id.audio_player);
    }

    @Override
    protected void init() {
        super.init();
        AppInjector.getAppComponent().inject(this);
        audioPlayerService.setListener(this);
        informationFragment = (ShowingInformationFragment) getActivity();
        long trackId = 0;
        if (getArguments() != null) {
            trackId = getArguments().getLong(Constants.BUNDLE_TRACK_ID);
            getArguments().clear();
        }
        viewModel.init(trackId);
        viewModel.getTrackModelLd().observe(getViewLifecycleOwner(), this::bindTrackInfo);
    }

    private void bindTrackInfo(TrackModel trackModel) {
        if (trackModel == null) return;
        if (((BaseActivity) getActivity()) != null)
            ((BaseActivity) getActivity()).initToolbar(trackModel.getTrackName(), true);

        ViewUtil.loadImage(albumCover, trackModel.getAlbumCoverBigUrl(), R.drawable.ic_audiotrack_24);
        trackName.setText(trackModel.getTrackName());
        String artistAndAlbumName = trackModel.getArtistName() + " - " + trackModel.getAlbumName();
        artistPlusAlbumName.setText(artistAndAlbumName);

        audioPlayer.setTrackUrl(trackModel.getTrackPreviewUrl());
    }

    @Override
    public void onStartPreparation() {
        audioPlayer.onStartPreparation();
        informationFragment.showProgressBar();
    }

    @Override
    public void onTrackPrepared() {
        audioPlayer.onTrackPrepared();
        informationFragment.hideProgressBar();
    }

    @Override
    public void onPrepareFailed() {
        audioPlayer.onPrepareFailed();
        informationFragment.showSnackbar(R.string.audio_player_error);
    }

    @Override
    public void onTrackChanged(TrackModel currentTrack) {
        audioPlayer.onTrackChanged(currentTrack);
        if (viewModel.getTrackModelLd().getValue() == currentTrack) return;
        bindTrackInfo(currentTrack);
    }

    @Override
    public void onEndQueue() {
        audioPlayer.onEndQueue();
    }

    @Override
    protected Class getViewModelClass() {
        return TrackDetailViewModel.class;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_track_detail;
    }
}


