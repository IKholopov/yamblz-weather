package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.data.repositories.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface WeatherView extends BaseView {

    void showCurrentWeather(CurrentWeather weather, OWSupportedMetric metric);

    void showErrorFragment();

}
