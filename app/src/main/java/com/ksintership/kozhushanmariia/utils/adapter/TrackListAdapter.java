package com.ksintership.kozhushanmariia.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.BindableTrackItem;
import com.ksintership.kozhushanmariia.model.TrackModel;

import java.util.ArrayList;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> {

    private LayoutInflater inflater;

    private List<TrackModel> tracks;
    private OnTrackItemClickListener onTrackItemClickListener;

    public TrackListAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public TrackListAdapter(Context context, List<TrackModel> tracks) {
        this.tracks = tracks;
        inflater = LayoutInflater.from(context);
    }

    public void setTracks(List<TrackModel> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    public void setOnTrackItemClickListener(OnTrackItemClickListener onTrackItemClickListener) {
        this.onTrackItemClickListener = onTrackItemClickListener;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackViewHolder(inflater.inflate(R.layout.track_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        if (holder.getView() instanceof BindableTrackItem) {
            final TrackModel trackModel = tracks.get(position);
            ((BindableTrackItem) holder.getView())
                    .bind(trackModel.getTrackName(),
                            trackModel.getArtistName(),
                            trackModel.getAlbumCoverMediumUrl());
            holder.getView().setOnClickListener(view -> onTrackItemClickListener.onItemClick(trackModel));
        }
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public View getView() {
            return view;
        }
    }

    public interface OnTrackItemClickListener {
        void onItemClick(TrackModel trackModel);
    }

}

