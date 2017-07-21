package com.example.toor.yamblzweather.domain.interactors;

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

    private Single<CurrentWeather> getCurrentWeather(Coord coord) {
        return weatherRepository.getCurrentWeather(coord);
    }

    private Single<ExtendedWeather> getExtendedWeather(Coord coord) {
        return weatherRepository.getExtendedWeather(coord);
    }

    private Single<SettingsModel> getTemperatureMetric() {
        return settingsRepository.getUserSettings();
    }

    public Single<FullWeatherModel> getFullWeather(Coord coord) {
        return Single.zip(getCurrentWeather(coord), getExtendedWeather(coord), getTemperatureMetric(), this::convert);
    }

    private FullWeatherModel convert(CurrentWeather currentWeather, ExtendedWeather extendedWeather, SettingsModel settingsModel) {
        return new FullWeatherModel(currentWeather, extendedWeather, settingsModel.getMetric());
    }

    public void saveWeather(FullWeatherModel fullWeatherModel) {
        weatherRepository.saveWeather(fullWeatherModel);
    }

    public void saveSelectedCity(Coord coord) {
        settingsRepository.saveSelectedCity(coord);
    }
}
