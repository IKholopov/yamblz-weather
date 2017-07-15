package com.example.toor.yamblzweather.data.settings;

import android.content.SharedPreferences;

import static com.example.toor.yamblzweather.data.settings.TemperatureType.CELSIUS;

public class SettingsPreference {

    private static final String temperatureKey = "temperatureKey";
    private static final String intervalKey = "intervalKey";
    private static final long MIN_UPDATE_INTERVAL = 60000;

    private SharedPreferences sharedPreferences;

    public SettingsPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveTemperatureType(TemperatureType type) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(temperatureKey, type.ordinal());
        editor.apply();
    }

    public TemperatureType loadTemperatureType() {
        int temp = sharedPreferences.getInt(temperatureKey, CELSIUS.ordinal());
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
