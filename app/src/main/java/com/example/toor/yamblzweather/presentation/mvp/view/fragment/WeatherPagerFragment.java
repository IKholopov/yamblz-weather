package com.example.toor.yamblzweather.presentation.mvp.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.NavigateView;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.NoPlacesAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.WeatherPlacesPagerAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment with ViewPager for different weather in different pages
 */
public class WeatherPagerFragment extends BaseFragment implements NavigateView {

    private Unbinder unbinder;
    private WeatherPlacesPagerAdapter pagerAdapter;
    private NoPlacesAdapter noPlacesAdapter;
    private Snackbar snack;

    @Inject
    WeatherPresenter presenter;

    @BindView(R.id.weatherPager) ViewPager weatherPager;

    public WeatherPagerFragment() {
    }

    public static WeatherPagerFragment newInstance() {
        return new WeatherPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        snack = Snackbar.make(weatherPager, R.string.no_internet, Snackbar.LENGTH_SHORT);
        View snackView = snack.getView();
        TextView tv = snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        noPlacesAdapter = new NoPlacesAdapter(getChildFragmentManager());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        presenter.updatePlacesList();
        presenter.getPlaces().subscribe(list -> {
            if(list == null || list.size() == 0) {
                noPlacesAdapter.setActive(true);
                weatherPager.setAdapter(noPlacesAdapter);
                noPlacesAdapter.notifyDataSetChanged();
            } else {
                noPlacesAdapter.setActive(false);
                noPlacesAdapter.notifyDataSetChanged();
                pagerAdapter = new WeatherPlacesPagerAdapter(list, getChildFragmentManager());
                weatherPager.setAdapter(null);
                weatherPager.setAdapter(pagerAdapter);
                pagerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showNetworkError() {
        if(!snack.isShown()) {
            snack.show();
        }
    }

    @Override
    public void navigateTo(int position) {
        weatherPager.setCurrentItem(position);
    }
}
