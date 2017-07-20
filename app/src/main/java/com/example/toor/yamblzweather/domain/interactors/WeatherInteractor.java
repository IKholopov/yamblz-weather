package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.models.weather.common.City;
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

    private Single<CurrentWeather> getCurrentWeather(City city) {
        return weatherRepository.getCurrentWeather(city);
    }

    private Single<ExtendedWeather> getExtendedWeather(City city) {
        return weatherRepository.getExtendedWeather(city);
    }

    private Single<SettingsModel> getTemperatureMetric() {
        return settingsRepository.getUserSettings();
    }

    public Single<FullWeatherModel> getFullWeather(City city) {
        return Single.zip(getCurrentWeather(city), getExtendedWeather(city), getTemperatureMetric(), this::convert);
    }

    private FullWeatherModel convert(CurrentWeather currentWeather, ExtendedWeather extendedWeather, SettingsModel settingsModel) {
        return new FullWeatherModel(currentWeather, extendedWeather, settingsModel.getMetric());
    }
}
