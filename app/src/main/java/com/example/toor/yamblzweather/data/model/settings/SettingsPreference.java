package com.example.toor.yamblzweather.data.model.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

public class SettingsPreference {

    private static final String temperatureKey = "temperatureKey";
    private static final String intervalKey = "intervalKey";
    private static final long MIN_UPDATE_INTERVAL = 60000;

    @Inject
    SharedPreferences sharedPreferences;

    public SettingsPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveTemperatureType(TemperatureType type) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(temperatureKey, type.ordinal());
        editor.apply();
    }

    public TemperatureType loadTemperatureType() {
        int temp = sharedPreferences.getInt(temperatureKey, 1);
        return TemperatureType.values()[temp];
    }

    public void saveUpdateWeatherInterval(long interval) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(intervalKey, interval);
        editor.apply();
    }

    public long loadUpdateWeatherInterval() {
        return sharedPreferences.getLong(intervalKey, MIN_UPDATE_INTERVAL);
    }
}
