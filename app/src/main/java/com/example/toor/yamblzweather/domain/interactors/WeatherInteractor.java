package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.settings.SettingsPreference;
import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherInteractor {

    @Inject
    OWService service;
    @Inject
    Locale locale;
    @Inject
    SettingsPreference preference;

    public WeatherInteractor() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    public Observable<CurrentWeather> getCurrentWeather(Coord coordinates) {
        service.setLanguage(locale);
        service.setMetricUnits(preference.loadTemperatureMetric());
        return service.getCurrentDayForecast(coordinates);
    }
}
