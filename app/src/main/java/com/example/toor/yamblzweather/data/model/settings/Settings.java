package com.example.toor.yamblzweather.data.model.settings;

public class Settings {

    private TemperatureType temperatureType;
    private long updateWeatherInterval;

    public Settings(TemperatureType temperatureType, long updateWeatherInterval) {
        this.temperatureType = temperatureType;
        this.updateWeatherInterval = updateWeatherInterval;
    }

    public void setTemperatureType(TemperatureType temperatureType) {
        this.temperatureType = temperatureType;
    }

    public void setUpdateWeatherInterval(long timeInterval) {
        this.updateWeatherInterval = timeInterval;
    }

    public TemperatureType getTemperatureType() {
        return this.temperatureType;
    }

    public long getUpdateWeatherInterval() {
        return this.updateWeatherInterval;
    }
}
