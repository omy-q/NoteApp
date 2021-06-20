package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NotesListFragment extends Fragment {

    private Note currentNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesList(view);
    }

    private void initNotesList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notesName = getResources().getStringArray(R.array.notesName);
        for (int i = 0; i < notesName.length; i++) {
            String noteName = notesName[i];
            TextView noteNameView = new TextView(getContext());
            noteNameView.setText(noteName);
            noteNameView.setTextSize(getResources().getDimension(R.dimen.noteNameSize));
            linearLayout.addView(noteNameView);

            final int index = i;
            clickedView(index, noteNameView);

        }
    }

    private void clickedView(int index, TextView textView) {
        textView.setOnClickListener(v -> {
            currentNote = new Note(index,
                    getResources().getStringArray(R.array.notesName)[index],
                    getResources().getStringArray(R.array.notesDescription)[index],
                    getResources().getStringArray(R.array.notesDate)[index]);
            showCurrentNote(currentNote);
        });
    }

    private void showCurrentNote(Note currentNote) {
        NoteEditorFragment editorFragment = NoteEditorFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_list, editorFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
