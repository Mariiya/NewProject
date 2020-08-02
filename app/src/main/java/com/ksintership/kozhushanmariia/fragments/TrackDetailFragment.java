package com.ksintership.kozhushanmariia.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.contract.ShowingInformationFragment;
import com.ksintership.kozhushanmariia.fragments.base.BaseFragment;
import com.ksintership.kozhushanmariia.viewmodels.TrackDetailViewModel;
import com.ksintership.kozhushanmariia.views.AudioPlayer;

public class TrackDetailFragment extends BaseFragment<TrackDetailViewModel> {

    private ImageView albumCover;
    private TextView trackName;
    private TextView artistPlusAlbumName;

    private AudioPlayer audioPlayer;

    @Override
    protected void initViews() {
        ((BaseActivity) getActivity()).initToolbar("Track detail", true);
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
    protected Class getViewModelClass() {
        return TrackDetailViewModel.class;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_track_detail;
    }
}


