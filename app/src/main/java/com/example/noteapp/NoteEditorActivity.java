package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class NoteEditorActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        initToolBar();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            NoteEditorFragment noteEditorFragment = new NoteEditorFragment();
            noteEditorFragment.setArguments(getIntent().getExtras());
// Добавим фрагмент на activity
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, noteEditorFragment).commit();
        }
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_editor);
        setSupportActionBar(toolbar);
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu_read_note, menu);
        return true;
    }
}