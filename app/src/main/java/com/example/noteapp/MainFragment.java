package com.example.noteapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.data.Note;
import com.example.noteapp.data.NoteSource;
import com.example.noteapp.data.NoteSourceFirebaseImpl;
import com.example.noteapp.data.NoteSourceImpl;
import com.example.noteapp.data.NoteSourceResponse;
import com.example.noteapp.ui.NoteListAdapter;

import java.util.List;


public class MainFragment extends Fragment {

    private Note currentNote;
    private int currentPosition;
    private boolean isLandscape;
    private boolean isDeleteNote = false;

    private static final String CURRENT_NOTE = "note";
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private NoteSource data;
    private NoteListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView();
        data = new NoteSourceFirebaseImpl().init(new NoteSourceResponse() {
            @Override
            public void initialized(NoteSource noteSource) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        onSearchItemSelected(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (isMainMenuItem(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isMainMenuItem(int id) {
        switch (id) {
            case R.id.settings:
                showMessage("settings");
                return true;
            case R.id.add_note:
                showMessage("replace Fragment - add note");
                data.addNote(new Note(data.size(), "Note" + (data.size() + 1), "", "", false));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.help:
                showMessage("help");
                return true;
            case R.id.favorite:
                showMessage("favorite");
                currentNote.setNoteFavorite(true);
                data.updateNote(currentNote.getPosIndex(), currentNote);
                return true;
            case R.id.delete:
                DialogFragment dlg = new DialogDeleteNoteFragment();
                dlg.show(requireActivity().getSupportFragmentManager(), "transactionTag");
                showMessage("delete");

                return true;
            case R.id.share_note:
                showMessage("share");
                return true;
        }
        return false;
    }

    public void onDialogResult(boolean resultDialog) {
        isDeleteNote = resultDialog;
        if (isDeleteNote) {
            data.deleteNote(currentNote);
            adapter.notifyItemRemoved(currentPosition);
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean onSearchItemSelected(Menu menu) {
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showMessage(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showMessage(newText);
                return true;
            }
        });
        return true;
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
                currentNote = data.getNote(position);
                currentPosition = position;
                showCurrentNote();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initNotesList(view);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
//            currentNote = data.getNote(0);
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

    private void showCurrentNote() {
        if (isLandscape) {
            showLandView();
        } else {
            showPortView();
        }
    }

    private void showLandView() {
        EditorFragment editorFragment = EditorFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, editorFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortView() {
        EditorFragment editorFragment = EditorFragment.newInstance(currentNote);
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
