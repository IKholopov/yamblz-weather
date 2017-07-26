package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface WeatherView extends BaseView {

    void showCurrentWeather(FullWeatherModel fullWeatherModel, String plaveName);

    void showErrorFragment();

}
