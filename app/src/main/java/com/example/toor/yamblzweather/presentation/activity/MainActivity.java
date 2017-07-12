package com.example.toor.yamblzweather.presentation.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.fragment.InfoFragment;
import com.example.toor.yamblzweather.presentation.fragment.SettingsFragment;
import com.example.toor.yamblzweather.presentation.fragment.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.example.toor.yamblzweather.presentation.fragment.WeatherFragment.WEATHER_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nav_view)
    NavigationView nvDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        setupDrawerContent(nvDrawer);

        FragmentManager fragmentManager = getFragmentManager();
        WeatherFragment weatherFragment = ((WeatherFragment) fragmentManager.findFragmentByTag(WEATHER_FRAGMENT_TAG));
        if (weatherFragment == null) {
            weatherFragment = WeatherFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.flContent, weatherFragment, WEATHER_FRAGMENT_TAG).commit();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        setupToolbar();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    void selectDrawerItem(MenuItem menuItem) {
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_weather:
                fragmentClass = WeatherFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_info:
                fragmentClass = InfoFragment.class;
                break;
            default:
                fragmentClass = WeatherFragment.class;
        }

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getSimpleName());
        if (fragment == null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment, fragmentClass.getSimpleName()).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
