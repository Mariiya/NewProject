package com.ksintership.kozhushanmariia.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.fragments.NoteListFragment;
import com.ksintership.kozhushanmariia.utils.adapter.NoteListAdapter;


public class MainActivity extends BaseActivity {
    public static int CHANGE_NOTE_REQUEST_CODE = 1;

    private NoteListFragment noteListFragment;

    private NoteListAdapter.OnNoteItemClickListener onItemClickListener;
    private NoteListFragment.OnAddNoteClickListener onAddNoteClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar(getString(R.string.app_name));

        initListeners();

        noteListFragment = (NoteListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_one);
        noteListFragment.setOnItemClickListener(onItemClickListener);
        noteListFragment.setOnAddNoteClickListener(onAddNoteClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            noteListFragment.addOrChangeNoteModel(data.getParcelableExtra(SecondActivity.BUNDLE_NOTE_MODEL));
        }
    }

    private void initListeners() {
        onAddNoteClickListener = () -> {
            startActivityForResult(SecondActivity.createIntent(this, null), CHANGE_NOTE_REQUEST_CODE);
        };
        onItemClickListener = noteModel -> {
            startActivityForResult(SecondActivity.createIntent(this, noteModel), CHANGE_NOTE_REQUEST_CODE);
        };
    }

}