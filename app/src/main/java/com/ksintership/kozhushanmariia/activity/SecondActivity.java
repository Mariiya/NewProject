package com.ksintership.kozhushanmariia.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.fragments.NoteDetailFragment;
import com.ksintership.kozhushanmariia.model.NoteModel;

public class SecondActivity extends BaseActivity {
    public static String BUNDLE_NOTE_MODEL = "bundle_note_model";

    private NoteModel noteModel;

    private NoteDetailFragment noteDetailFragment;
    private NoteDetailFragment.OnSaveNoteClickListener onSaveNoteClickListener;


    public static Intent createIntent(Context packageContext, @Nullable NoteModel noteModel) {
        Intent intent = new Intent(packageContext, SecondActivity.class);
        if (noteModel == null) return intent;
        intent.putExtra(BUNDLE_NOTE_MODEL, noteModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteModel = getIntent().getParcelableExtra(BUNDLE_NOTE_MODEL);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_second);
        initToolbar(noteModel != null ? noteModel.getTitle() : "New note", R.menu.edit_note_menu, true);

        initListeners();
        noteDetailFragment = (NoteDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_two);
        if (noteModel == null) {
            noteModel = new NoteModel();
        }
        noteDetailFragment.initNoteDetailFragment(noteModel, onSaveNoteClickListener);
    }

    private void initListeners() {
        onSaveNoteClickListener = viewModel -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(BUNDLE_NOTE_MODEL, noteModel);
            setResult(RESULT_OK, resultIntent);
            finish();
        };
    }

}