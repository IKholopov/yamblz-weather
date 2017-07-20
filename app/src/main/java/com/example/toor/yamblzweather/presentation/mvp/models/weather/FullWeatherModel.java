package com.example.toor.yamblzweather.presentation.mvp.models.weather;

import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;

public class FullWeatherModel {

    private CurrentWeather currentWeather;
    private ExtendedWeather weatherForecast;
    private TemperatureMetric metric;

    public FullWeatherModel(CurrentWeather currentWeather, ExtendedWeather weatherForecast, TemperatureMetric metric) {
        this.currentWeather = currentWeather;
        this.weatherForecast = weatherForecast;
        this.metric = metric;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public ExtendedWeather getWeatherForecast() {
        return weatherForecast;
    }

    public TemperatureMetric getTemperatureMetric() {
        return metric;
    }
}
