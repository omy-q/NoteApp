package com.example.noteapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

//    companion object {
//        private const val FRAGMENT_TRANSFER : String = "DATA_TRANSFER"
//    }
    private var isLandscape : Boolean = false
    private lateinit var fragment : Fragment

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        initView()
    }

    private fun initView() {
        val toolbar : Toolbar = initToolBar()
        initDrawer(toolbar)
        initMainFragment()
    }

    private fun initToolBar(): Toolbar {
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        return toolbar
    }

    private fun initDrawer(toolbar : Toolbar) {
        val drawer : DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        initNavigationView(drawer)
    }

    private fun initNavigationView(drawer : DrawerLayout) {
        val navigationView : NavigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener{item : MenuItem ->
            val id : Int = item.itemId
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START)
                true
            } else {
                false
            }
        }
    }

    private fun initMainFragment() {
        fragment = supportFragmentManager.findFragmentByTag("TAG") ?: MainFragment.newInstance()

        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, "TAG")
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }

    private fun showMessage(msg : String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    private fun navigateFragment(id : Int) : Boolean {
        return when (id) {
            R.id.settings -> {
                showMessage("settings")
                true
            }
            R.id.help -> {
                showMessage("help")
                true
            }
            R.id.all_notes_list -> {
                showMessage("replace to MainFragment")
                true
            }
            R.id.favorite_list -> {
                showMessage("replace to Fragment with favorite notes")
                true
            }
            else -> false
        }
    }

}
