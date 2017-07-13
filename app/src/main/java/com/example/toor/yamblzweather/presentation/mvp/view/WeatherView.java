package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.data.WeatherRepository.WeatherRepository;

public interface WeatherView {

    void showCurrentWeather(WeatherRepository weather);
}
