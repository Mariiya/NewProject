package com.ksintership.kozhushanmariia.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.contract.ShowingInformationFragment;
import com.ksintership.kozhushanmariia.di.AppInjector;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.model.TrackModel;
import com.ksintership.kozhushanmariia.repository.TrackRepository;
import com.ksintership.kozhushanmariia.utils.Constants;
import com.ksintership.kozhushanmariia.utils.ViewUtil;
import com.ksintership.kozhushanmariia.viewmodels.TrackDetailViewModel;
import com.ksintership.kozhushanmariia.views.AudioPlayer;

import javax.inject.Inject;

public class TrackDetailFragment extends BaseFragment<TrackDetailViewModel> {

    @Inject
    TrackRepository trackRepository;

    private ImageView albumCover;
    private TextView trackName;
    private TextView artistPlusAlbumName;

    private AudioPlayer audioPlayer;

    @Override
    protected void initViews() {
        ((BaseActivity) getActivity()).initToolbar("Track information", true);
        audioPlayer.setShowingInformationFragment((ShowingInformationFragment) getActivity());
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
        long trackId = -1L;
        if (getArguments() != null) {
            trackId = getArguments().getLong(Constants.BUNDLE_TRACK_ID);
        }
        viewModel.init(trackId);
        viewModel.getTrackModelLd().observe(getViewLifecycleOwner(), this::bindTrackInfo);
    }

    private void bindTrackInfo(TrackModel trackModel) {
        ((BaseActivity) getActivity()).initToolbar(trackModel.getTrackName(), true);

        ViewUtil.loadImage(albumCover, trackModel.getAlbumCoverBigUrl(), R.drawable.ic_audiotrack_24);
        trackName.setText(trackModel.getTrackName());
        String artistAndAlbumName = trackModel.getArtistName() + " - " + trackModel.getAlbumName();
        artistPlusAlbumName.setText(artistAndAlbumName);

        audioPlayer.setTrackUrl(trackModel.getTrackPreviewUrl());
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


