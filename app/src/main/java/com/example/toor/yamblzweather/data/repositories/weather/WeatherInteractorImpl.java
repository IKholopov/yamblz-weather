package com.example.toor.yamblzweather.data.repositories.weather;

import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.weather.five_day.ExtendedWeather;

import io.reactivex.Single;

public class WeatherInteractorImpl implements WeatherInteractor {
    @Override
    public Single<CurrentWeather> getCurrentWeather() {
        return null;
    }

    @Override
    public Single<ExtendedWeather> getExtendedWeather() {
        return null;
    }
}
