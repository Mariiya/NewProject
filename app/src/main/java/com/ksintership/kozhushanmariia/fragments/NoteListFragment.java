package com.ksintership.kozhushanmariia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.app.NoteApplication;
import com.ksintership.kozhushanmariia.model.NoteModel;
import com.ksintership.kozhushanmariia.model.NoteRepository;
import com.ksintership.kozhushanmariia.utils.adapter.NoteListAdapter;

import javax.inject.Inject;

public class NoteListFragment extends Fragment {

    @Inject
    NoteRepository noteRepository;

    private RecyclerView list;
    private NoteListAdapter listAdapter;

    private Button addButton;

    private OnAddNoteClickListener onAddNoteClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteApplication.getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_list, container, false);
        list = v.findViewById(R.id.note_list);
        addButton = v.findViewById(R.id.add_note_btn);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        initList();
    }

    public void setOnItemClickListener(NoteListAdapter.OnNoteItemClickListener onNoteItemClickListener) {
        listAdapter.setOnNoteItemClickListener(onNoteItemClickListener);
    }

    private void initListeners() {
        addButton.setOnClickListener(view -> {
            onAddNoteClickListener.onAddClick();
        });
    }

    private void initList() {
        listAdapter = new NoteListAdapter(requireContext(), noteRepository.getAllNotes(), addButton.getHeight());
        list.setLayoutManager(new LinearLayoutManager(requireContext()));
        list.setAdapter(listAdapter);
    }

    public void addOrChangeNoteModel(NoteModel noteModel) {
        boolean isNewNote = noteRepository.addOrChangeNote(noteModel);
        listAdapter.notifyDataSetChanged();
        if (isNewNote) list.smoothScrollToPosition(list.getBottom());
    }

    public void setOnAddNoteClickListener(OnAddNoteClickListener onAddNoteClickListener) {
        this.onAddNoteClickListener = onAddNoteClickListener;
    }

    public interface OnAddNoteClickListener {
        void onAddClick();
    }
}