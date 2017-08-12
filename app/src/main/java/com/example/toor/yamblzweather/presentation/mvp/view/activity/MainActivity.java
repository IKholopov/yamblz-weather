package com.example.toor.yamblzweather.presentation.mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.presenter.MainActivityPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.NavigateView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.ViewNavigationController;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.DrawerListAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.InfoFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.SettingsFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherPagerFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DrawerLocker, OnBackBehaviour,
        ViewNavigationController {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject MainActivityPresenter presenter;

    @BindView(R.id.nav_view)
    NavigationView nvDrawer;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    RecyclerView places;

    private ActionBarDrawerToggle toggle;
    private DrawerListAdapter placesAdapter;
    private Action onBackAction;

    private CompositeDisposable disposables = new CompositeDisposable();
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getInstance().plusActivityComponent().inject(this);

        unbinder = ButterKnife.bind(this);
        places = nvDrawer.getHeaderView(0).findViewById(R.id.rvDrawerPlaces);
        setupDrawerContent(nvDrawer);

        if (savedInstanceState == null) {
            WeatherPagerFragment weatherFragment = WeatherPagerFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.flContent, weatherFragment, WeatherFragment.class.getSimpleName()).commit();
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        places.setLayoutManager(llm);
        disposables.add(presenter.getPlaces().subscribe(allPlaces -> {
            placesAdapter = new DrawerListAdapter(allPlaces, this);
            places.setAdapter(placesAdapter);
        }));
    }

    private void setupDrawerContent(NavigationView navigationView) {
        setupToolbar();
        navigationView.setNavigationItemSelectedListener(item -> {
            selectDrawerItem(item);
            return true;
        });
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(view -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                setDrawerUnlocked();
            }
            Timber.v("click");
        });
    }

    void selectDrawerItem(MenuItem menuItem) {
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_info:
                fragmentClass = InfoFragment.class;
                break;
            default:
                fragmentClass = WeatherFragment.class;
        }

        popFragment(fragmentClass);

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(onBackAction != null) {
            try {
                onBackAction.run();
                return;
            } catch (Exception e) {
                Log.e(TAG, "Failed to run onBackAction!");
            }
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            setDrawerUnlocked();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        disposables.dispose();
        App.getInstance().releaseActivityComponent();
        super.onDestroy();
    }

    @Override
    public void setDrawerEnable(boolean enabled) {
        if (enabled)
            setDrawerUnlocked();
        else
            setDrawerLocked();
    }

    @Override
    public void setOnBackBehaviour(Action action) {
        onBackAction = action;
    }

    private void popFragment(Class fragmentClass) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentClass.getSimpleName());
        if (fragment == null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, fragment, fragmentClass.getSimpleName())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDrawerLocked() {
        toggle.setDrawerIndicatorEnabled(false);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(R.drawable.ic_close);
    }

    private void setDrawerUnlocked() {
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
        if(placesAdapter != null) {
            disposables.add(presenter.getPlaces().subscribe(places -> {
                placesAdapter.setPlaces(places);
            }));
        }
    }

    @Override
    public void switchToView(int position) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.flContent);
        if(f != null && f instanceof NavigateView) {
            ((NavigateView) f).navigateTo(position);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
