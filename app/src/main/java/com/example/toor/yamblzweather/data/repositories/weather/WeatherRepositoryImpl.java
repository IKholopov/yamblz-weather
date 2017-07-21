package com.example.toor.yamblzweather.data.repositories.weather;

import android.content.Context;

import com.example.toor.yamblzweather.data.database.DataBase;
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
    public Single<CurrentWeather> getCurrentWeather(int cityId) {
        return dataBase.loadCurrentWeather(cityId).onErrorResumeNext(throwable -> service.getCurrentWeather(cityId));
    }

    @Override
    public Single<ExtendedWeather> getExtendedWeather(int cityId) {
        return dataBase.loadWeatherForecast(cityId).onErrorResumeNext(throwable -> service.getExtendedWeather(cityId));
    }

    @Override
    public void saveWeather(FullWeatherModel weather) {
        dataBase.saveOrUpdateWeather(weather);
    }
}