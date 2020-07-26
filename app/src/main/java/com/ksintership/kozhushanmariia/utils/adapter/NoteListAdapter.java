package com.ksintership.kozhushanmariia.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.contract.BindableNoteItem;
import com.ksintership.kozhushanmariia.model.NoteModel;
import com.ksintership.kozhushanmariia.utils.ViewUtil;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private static final int HEADER_VIEW_TYPE = 1;
    private static final int NOTE_ITEM_VIEW_TYPE = 2;
    // this type of view is used so that the addButton does not overlap a last item of the list
    private static final int BOTTOM_EMPTY_STUB = 3;

    private Context context;
    private LayoutInflater inflater;

    private List<NoteModel> notes;
    private OnNoteItemClickListener onNoteItemClickListener;

    private int addButtonHeight;

    public NoteListAdapter(Context context, List<NoteModel> notes, int addButtonHeight) {
        this.notes = notes;
        this.addButtonHeight = addButtonHeight;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnNoteItemClickListener(OnNoteItemClickListener onNoteItemClickListener) {
        this.onNoteItemClickListener = onNoteItemClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case NOTE_ITEM_VIEW_TYPE:
                return new NoteViewHolder(inflater.inflate(R.layout.item_list_note, parent, false));
            case HEADER_VIEW_TYPE:
                return new NoteViewHolder(inflater.inflate(R.layout.item_list_header, parent, false));
            case BOTTOM_EMPTY_STUB:
                View view = new View(context);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        addButtonHeight + ViewUtil.dpToPx(context.getResources()
                                .getDimension(R.dimen.default_middle_margin) * 2)));
                return new NoteViewHolder(view);
        }
        return new NoteViewHolder(new View(context));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (holder.getView() instanceof BindableNoteItem) {
            final NoteModel noteModel = notes.get(position - 1);
            ((BindableNoteItem) holder.getView()).bind(noteModel.getTitle(), noteModel.getContent());
            holder.getView().setOnClickListener(view -> onNoteItemClickListener.onItemClick(noteModel));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER_VIEW_TYPE;
        else if (position == getItemCount() - 1) return BOTTOM_EMPTY_STUB;
        else return NOTE_ITEM_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return notes.size() + 2;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public View getView() {
            return view;
        }
    }

    public interface OnNoteItemClickListener {
        void onItemClick(NoteModel noteModel);
    }

}

