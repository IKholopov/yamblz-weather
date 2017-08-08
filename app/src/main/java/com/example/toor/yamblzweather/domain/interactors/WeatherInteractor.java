package com.example.toor.yamblzweather.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.reactivex.Single;

public class WeatherInteractor {

    private WeatherRepository weatherRepository;
    private SettingsRepository settingsRepository;

    public WeatherInteractor(WeatherRepository weatherRepository, SettingsRepository settingsRepository) {
        this.weatherRepository = weatherRepository;
        this.settingsRepository = settingsRepository;
    }

    private
    @NonNull
    Single<ExtendedWeather> getExtendedWeather(PlaceDetails placeDetails) {
        return weatherRepository.getExtendedWeatherFromDB(placeDetails)
                .onErrorResumeNext(throwable -> weatherRepository.loadExtendedWeatherFromNW(placeDetails));
    }

    private
    @NonNull
    Single<ExtendedWeather> updateExtendedWeather(PlaceDetails placeDetails) {
        return weatherRepository.loadExtendedWeatherFromNW(placeDetails)
                .onErrorResumeNext(throwable -> weatherRepository.getExtendedWeatherFromDB(placeDetails));
    }

    private
    @NonNull
    Single<SettingsModel> getTemperatureMetric() {
        return settingsRepository.getUserSettings();
    }

    public
    @NonNull
    Single<ExtendedWeather> getWeatherFromDB(PlaceModel place) {
        PlaceDetails details = detailsFromModel(place);
        return getExtendedWeather(details)
                .doOnSuccess(weatherModel -> weatherRepository.saveWeather(weatherModel, details));
    }

    public
    @NonNull
    Single<ExtendedWeather> updateWeather(PlaceModel place) {
        PlaceDetails details = detailsFromModel(place);
        return updateExtendedWeather(details)
                .doOnSuccess(weatherModel -> weatherRepository.saveWeather(weatherModel, details));
    }

    public
    @NonNull
    Single<Long> clearOldRecords() {
        Calendar date = GregorianCalendar.getInstance();
        return weatherRepository.clearOldRecords(date);
    }

    private
    @NonNull
    PlaceDetails detailsFromModel(PlaceModel place) {
        return PlaceDetails.newInstance(place.getLocalId(),
                new Coord(place.getLat(), place.getLon()), place.getName());
    }
}
