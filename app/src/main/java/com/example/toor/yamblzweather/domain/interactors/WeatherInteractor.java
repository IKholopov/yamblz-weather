package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.model.gson.common.Coord;
import com.example.toor.yamblzweather.data.model.gson.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.providers.CurrentWeatherProvider;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherInteractor {

    private CurrentWeatherProvider provider;

    @Inject
    Locale locale;

    @Inject
    OWService service;

    public WeatherInteractor(CurrentWeatherProvider provider) {
        this.provider = provider;

        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    public Observable<CurrentWeather> getCurrentWeather(Coord coordinates) {
        provider.provideService();
        service.setLanguage(locale);
        return service.getCurrentDayForecast(coordinates);
    }
}
