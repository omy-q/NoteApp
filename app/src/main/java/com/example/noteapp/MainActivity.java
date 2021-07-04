package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TRANSFER = "DATA_TRANSFER";
    private boolean isLandscape;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        initView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initView() {
        Toolbar toolbar = initToolBar();
        initDrawer(toolbar);
        initMainFragment();
    }

    private Toolbar initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        initNavigationView(drawer);
    }

    private void initNavigationView(DrawerLayout drawer) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private void initMainFragment() {
        fragment = getSupportFragmentManager().findFragmentByTag("TAG");
        if (fragment == null) {
            fragment = MainFragment.newInstance();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, "TAG");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        menu.clear();
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return onSearchItemSelected(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (navigateFragment(id)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

//    private boolean onSearchItemSelected(Menu menu) {
//        MenuItem search = menu.findItem(R.id.search);
//        SearchView searchText = (SearchView) search.getActionView();
//        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                showMessage(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                showMessage(newText);
//                return true;
//            }
//        });
//        return true;
//    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.settings:
                showMessage("settings");
                return true;
            case R.id.help:
                showMessage("help");
                return true;
            case R.id.all_notes_list:
                showMessage("replace to MainFragment");
                return true;
            case R.id.favorite_list:
                showMessage("replace to Fragment with favorite notes");
                return true;
        }
        return false;
    }
}