package com.example.toor.yamblzweather.data.settings;

import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;

public class Settings {

    private OWSupportedUnits metric;
    private long updateWeatherInterval;

    public Settings(OWSupportedUnits metric, long updateWeatherInterval) {
        this.metric = metric;
        this.updateWeatherInterval = updateWeatherInterval;
    }

    public void setOWSupportedUnits(OWSupportedUnits metric) {
        this.metric = metric;
    }

    public void setUpdateWeatherInterval(long timeInterval) {
        this.updateWeatherInterval = timeInterval;
    }

    public OWSupportedUnits getMetric() {
        return this.metric;
    }

    public long getUpdateWeatherInterval() {
        return this.updateWeatherInterval;
    }
}
