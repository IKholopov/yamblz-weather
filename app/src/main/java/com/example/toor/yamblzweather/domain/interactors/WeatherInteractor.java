package com.example.toor.yamblzweather.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;

import io.reactivex.Single;

public class WeatherInteractor {

    private WeatherRepository weatherRepository;
    private SettingsRepository settingsRepository;

    public WeatherInteractor(WeatherRepository weatherRepository, SettingsRepository settingsRepository) {
        this.weatherRepository = weatherRepository;
        this.settingsRepository = settingsRepository;
    }

    private
    @Nullable
    Single<CurrentWeather> getCurrentWeather(int cityId) {
        return weatherRepository.getCurrentWeatherFromDB(cityId)
                .onErrorResumeNext(throwable -> weatherRepository.loadCurrentWeatherFromNW(cityId));
    }

    private
    @Nullable
    Single<CurrentWeather> getCurrentWeather(Coord coords) {
        return weatherRepository.getCurrentWeatherFromDB(coords)
                .onErrorResumeNext(throwable -> weatherRepository.loadCurrentWeatherFromNW(coords));
    }

    private
    @Nullable
    Single<CurrentWeather> updateCurrentWeather(int cityId) {
        return weatherRepository.loadCurrentWeatherFromNW(cityId)
                .onErrorResumeNext(throwable -> weatherRepository.getCurrentWeatherFromDB(cityId));
    }

    private
    @Nullable
    Single<CurrentWeather> updateCurrentWeather(Coord coords) {
        return weatherRepository.loadCurrentWeatherFromNW(coords)
                .onErrorResumeNext(throwable -> weatherRepository.getCurrentWeatherFromDB(coords));
    }


    private
    @Nullable
    Single<ExtendedWeather> getExtendedWeather(int cityId) {
        return weatherRepository.getExtendedWeatherFromDB(cityId)
                .onErrorResumeNext(throwable -> weatherRepository.loadExtendedWeatherFromNW(cityId));
    }

    private
    @Nullable
    Single<ExtendedWeather> getExtendedWeather(Coord coords) {
        return weatherRepository.getExtendedWeatherFromDB(coords)
                .onErrorResumeNext(throwable -> weatherRepository.loadExtendedWeatherFromNW(coords));
    }

    private
    @Nullable
    Single<ExtendedWeather> updateExtendedWeather(int cityId) {
        return weatherRepository.loadExtendedWeatherFromNW(cityId)
                .onErrorResumeNext(throwable -> weatherRepository.getExtendedWeatherFromDB(cityId));
    }

    private
    @Nullable
    Single<ExtendedWeather> updateExtendedWeather(Coord coords) {
        return weatherRepository.loadExtendedWeatherFromNW(coords)
                .onErrorResumeNext(throwable -> weatherRepository.getExtendedWeatherFromDB(coords));
    }

    private
    @NonNull
    Single<SettingsModel> getTemperatureMetric() {
        return settingsRepository.getUserSettings();
    }

    public
    @Nullable
    Single<FullWeatherModel> getFullWeatherFromDB(int cityId) {
        return Single
                .zip(getCurrentWeather(cityId), getExtendedWeather(cityId), getTemperatureMetric(), this::convert)
                .doOnSuccess(fullWeatherModel -> weatherRepository.saveWeather(fullWeatherModel));
    }

    public
    @Nullable
    Single<FullWeatherModel> getFullWeatherFromDB(Coord coords) {
        return Single
                .zip(getCurrentWeather(coords), getExtendedWeather(coords), getTemperatureMetric(), this::convert)
                .doOnSuccess(fullWeatherModel -> weatherRepository.saveWeather(fullWeatherModel));
    }

    public
    @Nullable
    Single<FullWeatherModel> updateWeather(int cityId) {
        return Single
                .zip(updateCurrentWeather(cityId), updateExtendedWeather(cityId), getTemperatureMetric(), this::convert)
                .doOnSuccess(fullWeatherModel -> weatherRepository.saveWeather(fullWeatherModel));
    }

    public
    @Nullable
    Single<FullWeatherModel> updateWeather(Coord coords) {
        return Single
                .zip(updateCurrentWeather(coords), updateExtendedWeather(coords), getTemperatureMetric(), this::convert)
                .doOnSuccess(fullWeatherModel -> weatherRepository.saveWeather(fullWeatherModel));
    }

    private
    @Nullable
    FullWeatherModel convert(CurrentWeather currentWeather, ExtendedWeather extendedWeather, SettingsModel settingsModel) {
        return new FullWeatherModel(currentWeather, extendedWeather, settingsModel.getMetric());
    }
}
