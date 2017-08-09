package com.example.toor.yamblzweather.presentation.mvp.view.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.WeatherPlacesPagerAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment with ViewPager for different weather in different pages
 */
public class WeatherPagerFragment extends BaseFragment implements WeatherView {

    private Unbinder unbinder;
    private WeatherPlacesPagerAdapter pagerAdapter;

    @Inject
    WeatherPresenter presenter;

    @BindView(R.id.weatherPager) ViewPager weatherPager;

    public WeatherPagerFragment() {
        // Required empty public constructor
    }

    public static WeatherPagerFragment newInstance() {
        return new WeatherPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_weather);
    }

    @Override
    protected void setDrawableEnabled() {
        ((DrawerLocker) getActivity()).setDrawerEnable(true);
    }

    @Override
    protected void inject() {
        App.getInstance().plusActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_pager, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.updatePlacesList();
        presenter.getPlaces().subscribe(list -> {
            pagerAdapter = new WeatherPlacesPagerAdapter(list, getChildFragmentManager());
            weatherPager.setAdapter(null);
            weatherPager.setAdapter(pagerAdapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showWeather(DailyWeather weather, String placeName) {
        return;
    }

    @Override
    public void showErrorFragment() {

    }
}