package com.ksintership.kozhushanmariia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.activity.BaseActivity;
import com.ksintership.kozhushanmariia.model.NoteModel;
import com.ksintership.kozhushanmariia.utils.ViewUtil;


public class NoteDetailFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private OnSaveNoteClickListener onSaveNoteClickListener;
    private NoteModel noteModel;

    private TextView noteTitleView;
    private TextView noteContentView;

    public void initNoteDetailFragment(NoteModel noteModel, OnSaveNoteClickListener onSaveNoteClickListener) {
        this.noteModel = noteModel;
        noteTitleView.setText(noteModel.getTitle());
        noteContentView.setText(noteModel.getContent());
        this.onSaveNoteClickListener = onSaveNoteClickListener;
        initListeners();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_viewer, container, false);
        noteTitleView = v.findViewById(R.id.note_title);
        noteContentView = v.findViewById(R.id.note_content);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.save_note) {
            saveNote();
            return true;
        }
        return false;
    }

    private void initListeners() {
        ((BaseActivity) getActivity()).getToolbar().setOnMenuItemClickListener(this);
    }

    private void saveNote() {
        if (!confirmInput()) {
            ViewUtil.showSnackbar(noteTitleView, "One of the fields are empty");
            return;
        }
        noteModel.setTitle(noteTitleView.getText().toString());
        noteModel.setContent(noteContentView.getText().toString());
        onSaveNoteClickListener.onSave(noteModel);
    }

    private boolean confirmInput() {
        return !noteTitleView.getText().toString().isEmpty()
                && !noteContentView.getText().toString().isEmpty();
    }

    public interface OnSaveNoteClickListener {
        void onSave(NoteModel noteModel);
    }
}


