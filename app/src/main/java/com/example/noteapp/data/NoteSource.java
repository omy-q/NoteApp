package com.example.noteapp.data;

import com.example.noteapp.Note;

public interface NoteSource {
    Note getNote(int position);
    int size();
}
