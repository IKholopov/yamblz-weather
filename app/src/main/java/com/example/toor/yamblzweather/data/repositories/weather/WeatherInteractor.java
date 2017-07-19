package com.example.toor.yamblzweather.data.repositories.weather;

import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.weather.five_day.ExtendedWeather;

import io.reactivex.Single;

public interface WeatherInteractor {

    Single<CurrentWeather> getCurrentWeather();

    Single<ExtendedWeather> getExtendedWeather();
}
