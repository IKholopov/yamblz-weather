package com.example.toor.yamblzweather.data.repositories.weather;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;

import io.reactivex.Single;

public interface WeatherRepository {

    Single<CurrentWeather> getCurrentWeather(Coord coord);

    Single<ExtendedWeather> getExtendedWeather(Coord coord);

    void saveWeather(FullWeatherModel weather);
}