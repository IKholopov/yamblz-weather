package com.example.toor.yamblzweather.data.repositories.weather;

import android.content.Context;

import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.settings.SettingsPreference;
import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private DataBase dataBase;
    private OWService service;
    private SettingsPreference preference;

    @Inject
    Context context;
    @Inject
    Locale locale;

    public WeatherRepositoryImpl(DataBase dataBase, OWService service, SettingsPreference preference) {
        this.dataBase = dataBase;
        this.service = service;
        this.preference = preference;

        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    @Override
    public Single<CurrentWeather> getCurrentWeather(City city) {
        Single<CurrentWeather> weather;
        if (NetworkConectionChecker.isNetworkAvailable(context)) {
            service.setLanguage(locale);
            service.setMetricUnits(preference.loadTemperatureMetric());
            weather = service.getCurrentWeather(city);
        } else
            weather = dataBase.loadCurrentWeather(city);
        return weather;
    }

    @Override
    public Single<ExtendedWeather> getExtendedWeather(City city) {
        Single<ExtendedWeather> weather;
        if (NetworkConectionChecker.isNetworkAvailable(context)) {
            service.setLanguage(locale);
            weather = service.getExtendedWeather(city);
        } else
            weather = dataBase.loadWeatherForecast(city);
        return weather;
    }

    @Override
    public void saveWeather(FullWeatherModel weather) {
        dataBase.saveWeather(weather);
    }
}