package com.ksintership.kozhushanmariia.model;

import java.util.List;

public interface NoteRepository {
    List<NoteModel> getAllNotes();

    void addNote(String title, String content);

    /**
     * @return true if noteModel was added and false if noteModel was changed
     */
    boolean addOrChangeNote(NoteModel noteModel);
}
