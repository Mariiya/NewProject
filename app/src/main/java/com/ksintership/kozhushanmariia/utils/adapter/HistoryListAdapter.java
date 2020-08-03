package com.ksintership.kozhushanmariia.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.BindableHistoryItem;
import com.ksintership.kozhushanmariia.model.SearchHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    private OnHistoryItemClickListener onClickListener;
    private LayoutInflater inflater;

    private List<SearchHistoryModel> list = new ArrayList<>();

    public HistoryListAdapter(Context context, OnHistoryItemClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }

    public void setList(List<SearchHistoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(inflater.inflate(R.layout.history_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        final SearchHistoryModel model = list.get(position);
        ((BindableHistoryItem) holder.view).bind(model.getHistoryQuery(),
                model.getSearchDate(),
                (v) -> onClickListener.onClearItemClick(model));

        holder.view.setOnClickListener((v) -> onClickListener.onHistoryItemClick(model));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        final View view;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public interface OnHistoryItemClickListener {

        void onClearItemClick(SearchHistoryModel model);

        void onHistoryItemClick(SearchHistoryModel model);
    }
}

