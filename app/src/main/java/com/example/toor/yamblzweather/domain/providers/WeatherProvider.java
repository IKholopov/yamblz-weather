package com.example.toor.yamblzweather.domain.providers;

import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherProvider {

    private OWService service;

    @Inject
    public WeatherProvider(OWService service) {
        this.service = service;
    }

    public void setLanguage(Locale locale) {
        service.setLanguage(locale);
    }

    public void setMetric(OWSupportedUnits metric) {
        service.setMetricUnits(metric);
    }

    public Observable<CurrentWeather> getCurrentForecast(Coord coordinates) {
        return service.getCurrentDayForecast(coordinates);
    }
}
