package com.example.noteapp.data;

import android.content.res.Resources;

import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {
    private List<Note> dataSource;
    private Resources resources;

    public NoteSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public NoteSourceImpl init() {
        String[] names = resources.getStringArray(R.array.notesName);
        String[] descriptions = resources.getStringArray(R.array.notesDescription);
        String[] dates = resources.getStringArray(R.array.notesDate);
        for (int i = 0; i < names.length; i++) {
            dataSource.add(new Note(i, names[i], descriptions[i], dates[i], false));
        }
        return this;
    }

    @Override
    public Note getNote(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteNote(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        dataSource.set(position, note);
    }

    @Override
    public void addNote(Note note) {
        dataSource.add(note);
    }
}

