package com.example.toor.yamblzweather.domain.interactors;

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

    private Single<CurrentWeather> currentWeather;
    private Single<ExtendedWeather> weatherForecast;

    public WeatherInteractor(WeatherRepository weatherRepository, SettingsRepository settingsRepository) {
        this.weatherRepository = weatherRepository;
        this.settingsRepository = settingsRepository;
    }

    private Single<CurrentWeather> getCurrentWeather(int cityId) {
        if (currentWeather == null) {
            currentWeather = weatherRepository.getCurrentWeather(cityId);
        }
        return currentWeather;
    }

    private Single<ExtendedWeather> getExtendedWeather(int cityId) {
        if (weatherForecast == null)
            weatherForecast = weatherRepository.getExtendedWeather(cityId);
        return weatherForecast;

    }

    private Single<SettingsModel> getTemperatureMetric() {
        return settingsRepository.getUserSettings();
    }

    public Single<FullWeatherModel> getFullWeather(int cityId) {
        return Single.zip(getCurrentWeather(cityId), getExtendedWeather(cityId), getTemperatureMetric(), this::convert);
    }

    private FullWeatherModel convert(CurrentWeather currentWeather, ExtendedWeather extendedWeather, SettingsModel settingsModel) {
        return new FullWeatherModel(currentWeather, extendedWeather, settingsModel.getMetric());
    }

    public void saveWeather(FullWeatherModel fullWeatherModel) {
        weatherRepository.saveWeather(fullWeatherModel);
    }
}
