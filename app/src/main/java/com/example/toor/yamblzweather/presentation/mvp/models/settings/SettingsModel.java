package com.example.toor.yamblzweather.presentation.mvp.models.settings;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;

public class SettingsModel {

    private final TemperatureMetric metric;
    private final long updateWeatherInterval;
    private final Coord selectedCityCoords;

    public static class Builder {
        //Requered params
        private final TemperatureMetric metric;
        private final long updateWeatherInterval;

        //Optional params
        private Coord coord = new Coord();

        public Builder(TemperatureMetric metric, long updateWeatherInterval) {
            this.metric = metric;
            this.updateWeatherInterval = updateWeatherInterval;
            coord.setLat(55.751244);
            coord.setLon(37.618423);
        }

        public Builder coords(Coord val) {
            coord = val;
            return this;
        }

        public SettingsModel build() {
            return new SettingsModel(this);
        }
    }


    public SettingsModel(Builder builder) {
        metric = builder.metric;
        updateWeatherInterval = builder.updateWeatherInterval;
        selectedCityCoords = builder.coord;
    }

    public TemperatureMetric getMetric() {
        return this.metric;
    }

    public long getUpdateWeatherInterval() {
        return this.updateWeatherInterval;
    }

    public Coord getSelectedCityCoords() {
        return this.selectedCityCoords;
    }
}