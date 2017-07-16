package com.example.toor.yamblzweather.data.settings;

import android.content.SharedPreferences;

import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.fromString;

public class SettingsPreference {

    private static final String temperatureKey = "temperatureKey";
    private static final String intervalKey = "intervalKey";
    private static final long MIN_UPDATE_INTERVAL = 60000;

    private SharedPreferences sharedPreferences;

    public SettingsPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveTemperatureMetric(OWSupportedUnits metric) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(temperatureKey, metric.getUnit());
        editor.apply();
    }

    public OWSupportedUnits loadTemperatureMetric() {
        String metric = sharedPreferences.getString(temperatureKey, CELSIUS.getUnit());
        return fromString(metric);
    }

    public void saveUpdateWeatherInterval(long interval) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(intervalKey, interval);
        editor.apply();
    }

    public long loadUpdateWeatherInterval() {
        return sharedPreferences.getLong(intervalKey, MIN_UPDATE_INTERVAL);
    }

    public Settings loadUserSettings() {
        OWSupportedUnits metric = loadTemperatureMetric();
        long interval = loadUpdateWeatherInterval();
        return new Settings(metric, interval);
    }
}
