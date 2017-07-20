package com.example.toor.yamblzweather.data.repositories.weather;

import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;
import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;

import io.reactivex.Single;

public interface WeatherRepository {

    Single<CurrentWeather> getCurrentWeather(City city);

    Single<ExtendedWeather> getExtendedWeather(City city);

    void saveWeather(FullWeatherModel weather);
}