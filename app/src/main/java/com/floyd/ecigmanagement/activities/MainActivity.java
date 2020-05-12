package com.floyd.ecigmanagement.activities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.net.Uri;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.floyd.ecigmanagement.R;
import com.floyd.ecigmanagement.fragments.AdminAromeFragment;
import com.floyd.ecigmanagement.fragments.AdminBoosterFragment;
import com.floyd.ecigmanagement.fragments.AromeFragment;
import com.floyd.ecigmanagement.fragments.BoosterFragment;
import com.floyd.ecigmanagement.fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, AromeFragment.OnFragmentInteractionListener, BoosterFragment.OnFragmentInteractionListener, AdminAromeFragment.OnFragmentInteractionListener, AdminBoosterFragment.OnFragmentInteractionListener{

    private static final String TAG = "MAIN_ACTIVITY";

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);

        // Find our drawer view
        nvDrawer = findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);;

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Set up home fragment on first arrival
        selectDrawerItem(nvDrawer.getCheckedItem());

        initToastsy();
    }

    private void initToastsy() {
        Toasty.Config.getInstance()
                .tintIcon(false)
                //.setTextSize(int sizeInSp)
                .setErrorColor(Color.RED)
                .setInfoColor(Color.BLUE)
                .setWarningColor(Color.YELLOW)
                .setSuccessColor(Color.GREEN)
                .setTextColor(Color.BLACK)
                .apply();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Log.d(TAG, "menuItem selected : " + menuItem.getTitle() + " (id=" + menuItem.getItemId() + ")");

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        String title = "";

        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                title = getString(R.string.menu_home);
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_arome:
                title = getString(R.string.menu_arome);
                fragmentClass = AromeFragment.class;
                break;
            case R.id.nav_booster:
                title = getString(R.string.menu_booster);
                fragmentClass = BoosterFragment.class;
                break;
            case R.id.nav_admin_arome:
                title = getString(R.string.menu_admin_arome);
                fragmentClass = AdminAromeFragment.class;
                break;
            case R.id.nav_admin_booster:
                title = getString(R.string.menu_admin_booster);
                fragmentClass = AdminBoosterFragment.class;
                break;
            default:
                title = getString(R.string.menu_home);
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        toolbar.setTitle(title);
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.d(TAG, "");
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "New configuration : " + newConfig.orientation);
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "");
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "MenuItem selected : " + item.getTitle());
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
