package com.example.noteapp;

import android.content.res.Configuration;
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

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment{

    private Note currentNote;
    private boolean isLandscape;

    private static final String CURRENT_NOTE = "note";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNotesList(view);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(0, getResources().getStringArray(R.array.notesName)[0],
                    getResources().getStringArray(R.array.notesDescription)[0],
                    getResources().getStringArray(R.array.notesDate)[0]);
        }
        if (isLandscape) {
            showLandView();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
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
            showCurrentNote();
        });
    }

    private void showCurrentNote() {

        if (isLandscape) {
            showLandView();
        } else {
            showPortView();
        }
    }

    private void showLandView() {
        EditorFragment editorFragment = EditorFragment.newInstance();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, editorFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortView() {
        EditorFragment editorFragment = EditorFragment.newInstance();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container, editorFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }


}
