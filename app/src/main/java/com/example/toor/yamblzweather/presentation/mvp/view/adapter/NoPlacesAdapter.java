package com.example.toor.yamblzweather.presentation.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.toor.yamblzweather.presentation.mvp.view.fragment.NoPlacesFragment;

/**
 * Created by igor on 8/12/17.
 */

public class NoPlacesAdapter extends FragmentPagerAdapter {

    private boolean active;

    public NoPlacesAdapter (@NonNull FragmentManager manager) {
        super(manager);
    }

    @Override
    public int getCount() {
        return active ? 1 : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return NoPlacesFragment.newInstance();
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
