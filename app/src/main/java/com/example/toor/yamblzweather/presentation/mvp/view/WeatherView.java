package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface WeatherView extends BaseView {

    void showWeather(DailyWeather weather, String placeName);

    void showWeather(DailyWeather weather);

    long getPlaceId();

    void showErrorFragment();

}
