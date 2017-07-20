package com.example.toor.yamblzweather.presentation.mvp.models.settings;

import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;

public class SettingsModel {

    private final TemperatureMetric metric;
    private final long updateWeatherInterval;

    public SettingsModel(TemperatureMetric metric, long updateWeatherInterval) {
        this.metric = metric;
        this.updateWeatherInterval = updateWeatherInterval;
    }

    public TemperatureMetric getMetric() {
        return this.metric;
    }

    public long getUpdateWeatherInterval() {
        return this.updateWeatherInterval;
    }
}