package com.example.toor.yamblzweather.domain.providers;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.data.settings.SettingsPreference;
import com.example.toor.yamblzweather.data.settings.TemperatureType;

import javax.inject.Inject;

public class SettingsProvider {

    private SettingsPreference preference;

    @Inject
    public SettingsProvider(SettingsPreference preference) {
        this.preference = preference;
    }

    public Settings loadSettings() {
        return new Settings(preference.loadTemperatureType(), preference.loadUpdateWeatherInterval());
    }

    public void saveSettings(Settings settings) {
        preference.saveTemperatureType(settings.getTemperatureType());
        preference.saveUpdateWeatherInterval(settings.getUpdateWeatherInterval());
    }

    public void saveTemperatureType(TemperatureType temperatureType) {
        preference.saveTemperatureType(temperatureType);
    }

    public void saveUpdateWeatherInterval(long interval) {
        preference.saveUpdateWeatherInterval(interval);
    }
}
