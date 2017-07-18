package com.example.toor.yamblzweather.data.settings;

import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;

public class Settings {

    private OWSupportedMetric metric;
    private long updateWeatherInterval;
    private Coord coordinates;

    public Settings(OWSupportedMetric metric, long updateWeatherInterval, Coord coordinates) {
        this.metric = metric;
        this.updateWeatherInterval = updateWeatherInterval;
        this.coordinates = coordinates;
    }

    public void setOWSupportedUnits(OWSupportedMetric metric) {
        this.metric = metric;
    }

    public void setUpdateWeatherInterval(long timeInterval) {
        this.updateWeatherInterval = timeInterval;
    }

    public OWSupportedMetric getMetric() {
        return this.metric;
    }

    public long getUpdateWeatherInterval() {
        return this.updateWeatherInterval;
    }

    public Coord getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coord coordinates) {
        this.coordinates = coordinates;
    }
}
