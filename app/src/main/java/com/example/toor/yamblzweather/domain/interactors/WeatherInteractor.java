package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.model.gson.common.Coord;
import com.example.toor.yamblzweather.data.model.gson.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.providers.CurrentWeatherProvider;
import com.example.toor.yamblzweather.domain.service.OWService;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherInteractor {

    @Inject
    CurrentWeatherProvider provider;

    public WeatherInteractor(CurrentWeatherProvider provider) {
        this.provider = provider;
    }

    public Observable<CurrentWeather> getCurrentWeather(Coord coordinates) {
        OWService service = provider.provideService();
        return service.getCurrentDayForecast(coordinates);
    }

}
