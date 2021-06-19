package com.example.noteapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesList(view);
    }

    private void initNotesList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] notesName = getResources().getStringArray(R.array.notesName);
        for(int i = 0; i < notesName.length; i++){
            String noteName = notesName[i];
            TextView noteNameView = new TextView(getContext());
            noteNameView.setText(noteName);
            noteNameView.setTextSize(getResources().getDimension(R.dimen.noteNameSize));
            linearLayout.addView(noteNameView);
        }
    }
}