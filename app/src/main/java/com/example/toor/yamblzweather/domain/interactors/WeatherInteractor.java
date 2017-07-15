package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.weather.gson.common.Coord;
import com.example.toor.yamblzweather.data.weather.gson.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.providers.WeatherProvider;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherInteractor {

    @Inject
    WeatherProvider provider;
    @Inject
    Locale locale;

    public WeatherInteractor(WeatherProvider provider) {
        this.provider = provider;

        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    public Observable<CurrentWeather> getCurrentWeather(Coord coordinates) {
        provider.setLanguage(locale);
        return provider.getCurrentForecast(coordinates);
    }
}
