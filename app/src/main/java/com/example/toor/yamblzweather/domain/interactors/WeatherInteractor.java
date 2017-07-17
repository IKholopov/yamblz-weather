package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.google.gson.Gson;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherInteractor extends BaseInteracor {

    @Inject
    OWService service;
    @Inject
    Locale locale;

    public Observable<CurrentWeather> getCurrentWeather(Coord coordinates) {
        service.setLanguage(locale);
        service.setMetricUnits(preference.loadTemperatureMetric());
        return service.getCurrentDayForecast(coordinates);
    }

    public OWSupportedMetric getTemperaturMertric() {
        return preference.loadTemperatureMetric();
    }

    public CurrentWeather loadCachedCurrentWeather() {
        Gson gson = new Gson();
        return gson.fromJson(preference.loadCurrentWeather(), CurrentWeather.class);
    }

    @Override
    protected void inject() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }
}
