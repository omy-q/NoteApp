package com.example.noteapp.data;

public interface NoteSource {
    NoteSource init(NoteSourceResponse noteSourceResponse);
    Note getNote(int position);
    int size();
    void deleteNote(Note note);
    void updateNote(int position, Note note);
    void addNote(Note note);

}
