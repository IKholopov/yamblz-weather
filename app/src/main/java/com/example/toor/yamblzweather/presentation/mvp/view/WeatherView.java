package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface WeatherView extends BaseView {

    void showWeather(DailyWeather weather, String placeName);

    void showWeather(DailyWeather weather);

    long getPlaceId();
}
