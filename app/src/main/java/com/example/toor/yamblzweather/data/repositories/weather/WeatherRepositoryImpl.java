package com.example.toor.yamblzweather.data.repositories.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private DataBase dataBase;
    private OWService service;

    @Inject
    Context context;
    @Inject
    Locale locale;

    public WeatherRepositoryImpl(DataBase dataBase, OWService service) {
        this.dataBase = dataBase;
        this.service = service;

        App.getInstance().getAppComponent().inject(this);
        service.setLanguage(locale);
    }

    @Override
    public Single<CurrentWeather> getCurrentWeatherFromDB(int cityId) {
        return dataBase.getCurrentWeather(cityId);
    }

    @Nullable
    @Override
    public Single<CurrentWeather> getCurrentWeatherFromDB(Coord coords) {
        return dataBase.getCurrentWeather(coords);
    }

    @Nullable
    @Override
    public Single<CurrentWeather> loadCurrentWeatherFromNW(int cityId) {
        return service.getCurrentWeather(cityId);
    }
    @Nullable
    @Override
    public Single<CurrentWeather> loadCurrentWeatherFromNW(Coord coord) {
        return service.getCurrentWeatherForCoords(coord);
    }

    @Override
    public Single<ExtendedWeather> getExtendedWeatherFromDB(int cityId) {
        return dataBase.getExtendedWeather(cityId);
    }

    @Nullable
    @Override
    public Single<ExtendedWeather> getExtendedWeatherFromDB(Coord coords) {
        return dataBase.getExtendedWeather(coords);
    }

    @Nullable
    @Override
    public Single<ExtendedWeather> loadExtendedWeatherFromNW(int cityId) {
        return service.getExtendedWeather(cityId);
    }

    @Nullable
    @Override
    public Single<ExtendedWeather> loadExtendedWeatherFromNW(Coord coords) {
        return service.getExtendedWeather(coords);
    }

    @Override
    public void saveWeather(@NonNull FullWeatherModel weather) {
        dataBase.saveWeather(weather);
    }
}