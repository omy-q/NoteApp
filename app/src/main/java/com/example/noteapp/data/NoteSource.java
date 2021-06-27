package com.example.noteapp.data;

public interface NoteSource {
    Note getNote(int position);
    int size();
}
