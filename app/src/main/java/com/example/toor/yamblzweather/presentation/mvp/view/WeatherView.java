package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface WeatherView extends BaseView {

    void showCurrentWeather(CurrentWeather weather, OWSupportedUnits metric);
}
