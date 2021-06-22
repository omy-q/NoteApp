package com.example.noteapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEditorFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private Note note;

    public static NoteEditorFragment newInstance(Note note) {
        NoteEditorFragment fragment = new NoteEditorFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_editor, container, false);
        EditText editor = view.findViewById(R.id.note_editor);
        String str = note.getNoteDescription() + "\n" + note.getNoteDate();
        editor.setText(str);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setHasOptionsMenu(true);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.context_menu_read_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share_note:
                showMessage("share note");
                return true;
            case R.id.favorite:
                showMessage("favorite");
                return true;
            case R.id.delete:
                showMessage("delete");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
