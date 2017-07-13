package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherScreenModule;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

public class WeatherFragment extends BaseFragment implements WeatherView  {

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_weather);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        App.getInstance().getAppComponent().plus(new WeatherModule()).plus(new WeatherScreenModule()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void showCurrentWeather() {

    }
}
