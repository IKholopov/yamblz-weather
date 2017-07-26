package com.example.toor.yamblzweather.presentation.mvp.models.settings;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;

public class SettingsModel {

    private final TemperatureMetric metric;
    private final long updateWeatherInterval;
    private final int selectedCityId;
    private final String cityName;
    private final Coord coords;

    public static class Builder {
        //Requered params
        private final TemperatureMetric metric;
        private final long updateWeatherInterval;

        //Optional params
        private int cityId;
        private Coord coords;
        private String cityName;

        public Builder(TemperatureMetric metric, long updateWeatherInterval) {
            this.metric = metric;
            this.updateWeatherInterval = updateWeatherInterval;
        }

        public Builder cityId(int val) {
            cityId = val;
            return this;
        }

        public Builder coords(Coord coords) {
            this.coords = coords;
            return this;
        }

        public Builder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public SettingsModel build() {
            return new SettingsModel(this);
        }
    }


    public SettingsModel(Builder builder) {
        metric = builder.metric;
        updateWeatherInterval = builder.updateWeatherInterval;
        selectedCityId = builder.cityId;
        coords = builder.coords;
        cityName = builder.cityName;
    }

    public TemperatureMetric getMetric() {
        return this.metric;
    }

    public long getUpdateWeatherInterval() {
        return this.updateWeatherInterval;
    }

    public int getSelectedCityId() {
        return this.selectedCityId;
    }

    public Coord getCityCoords() {
        return this.coords;
    }

    public String getCityName() {
        return cityName;
    }
}