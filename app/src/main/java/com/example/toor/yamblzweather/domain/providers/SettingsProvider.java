package com.example.toor.yamblzweather.domain.providers;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.data.settings.SettingsPreference;
import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;

import javax.inject.Inject;

public class SettingsProvider {

    private SettingsPreference preference;

    @Inject
    public SettingsProvider(SettingsPreference preference) {
        this.preference = preference;
    }

    public Settings loadSettings() {
        return new Settings(preference.loadTemperatureMetric(), preference.loadUpdateWeatherInterval());
    }

    public void saveSettings(Settings settings) {
        preference.saveTemperatureType(settings.getMetric());
        preference.saveUpdateWeatherInterval(settings.getUpdateWeatherInterval());
    }

    public void saveTemperatureMetric(OWSupportedUnits metric) {
        preference.saveTemperatureType(metric);
    }

    public void saveUpdateWeatherInterval(long interval) {
        preference.saveUpdateWeatherInterval(interval);
    }
}
