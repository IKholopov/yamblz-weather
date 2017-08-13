package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;

/**
 * Created by igor on 8/10/17.
 */

public interface WeatherDetailsView {
    void showWeatherDetails(DailyForecastElement element);
}
