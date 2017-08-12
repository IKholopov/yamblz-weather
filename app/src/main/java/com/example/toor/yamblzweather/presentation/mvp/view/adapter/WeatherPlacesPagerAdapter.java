package com.example.toor.yamblzweather.presentation.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherFragment;

import java.util.List;

/**
 * Created by igor on 8/6/17.
 */

public class WeatherPlacesPagerAdapter extends FragmentStatePagerAdapter {

    private List<PlaceModel> places;

    public WeatherPlacesPagerAdapter(@NonNull List<PlaceModel> places, FragmentManager manager) {
        super(manager);
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Fragment getItem(int position) {
        return WeatherFragment.newInstance(places.get(position), position);
    }
}
