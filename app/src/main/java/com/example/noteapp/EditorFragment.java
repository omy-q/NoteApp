package com.example.noteapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.noteapp.data.Note;

public class EditorFragment extends Fragment {

    private Note note;

    public static EditorFragment newInstance() {
        return new EditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_note_editor, container, false);
        EditText editor = view.findViewById(R.id.note_editor);
        if (note != null) {
            String str = note.getNoteDescription() + "\n" + note.getNoteDate();
            editor.setText(str);
        } else {
            String str1 = "sometext";
            editor.setText(str1);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.context_menu_read_note, menu);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.menu.main_menu, menu);
        }
    }

}
