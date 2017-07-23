package com.example.toor.yamblzweather.data.repositories.weather;

import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;

import io.reactivex.Single;

public interface WeatherRepository {

    @Nullable
    Single<CurrentWeather> getCurrentWeatherFromDB(int cityId);

    @Nullable
    Single<CurrentWeather> getCurrentWeatherFromDB(Coord coord);

    @Nullable
    Single<CurrentWeather> loadCurrentWeatherFromNW(int cityId);

    @Nullable
    Single<CurrentWeather> loadCurrentWeatherFromNW(Coord coord);

    @Nullable
    Single<ExtendedWeather> getExtendedWeatherFromDB(int cityId);

    @Nullable
    Single<ExtendedWeather> getExtendedWeatherFromDB(Coord coords);

    @Nullable
    Single<ExtendedWeather> loadExtendedWeatherFromNW(int cityId);

    @Nullable
    Single<ExtendedWeather> loadExtendedWeatherFromNW(Coord coords);

    void saveWeather(FullWeatherModel weather);
}