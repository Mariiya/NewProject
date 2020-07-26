package com.ksintership.kozhushanmariia.model;

import android.content.Context;

import com.ksintership.kozhushanmariia.R;

import java.util.ArrayList;
import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {
    private Context context;

    private ArrayList<NoteModel> notes;
    private long lastNoteId = -1;

    public NoteRepositoryImpl(Context context) {
        this.context = context;
        initNotes();
    }

    private void initNotes() {
        notes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            notes.add(new NoteModel(++lastNoteId, "Some note", context.getString(R.string.debug_lorem_ipsum)));
        }
    }

    @Override
    public List<NoteModel> getAllNotes() {
        return notes;
    }

    @Override
    public void addNote(String title, String content) {
        notes.add(new NoteModel(++lastNoteId, title, content));
    }

    @Override
    public boolean addOrChangeNote(NoteModel noteModel) {
        int index = notes.indexOf(noteModel);
        if (index != -1) {
            notes.get(index).setTitle(noteModel.getTitle());
            notes.get(index).setContent(noteModel.getContent());
            return false;
        } else {
            addNote(noteModel.getTitle(), noteModel.getContent());
            return true;
        }
    }
}
