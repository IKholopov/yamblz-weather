package com.example.toor.yamblzweather.data.settings;

import android.content.SharedPreferences;

import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedMetric.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.OWSupportedMetric.fromString;

public class SettingsPreference {

    private static final String temperatureKey = "temperatureKey";
    private static final String intervalKey = "intervalKey";
    private static final String latitudeKey = "latitudeKey";
    private static final String longitudeKey = "longitudeKey";

    private static final long MIN_UPDATE_INTERVAL = 1 * 60 * 60 * 1000; // interval is 1 hour

    private SharedPreferences sharedPreferences;

    public SettingsPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveTemperatureMetric(OWSupportedMetric metric) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(temperatureKey, metric.getUnit());
        editor.apply();
    }

    public OWSupportedMetric loadTemperatureMetric() {
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
        OWSupportedMetric metric = loadTemperatureMetric();
        long interval = loadUpdateWeatherInterval();
        Coord coordinates = loadCoordinates();
        return new Settings(metric, interval, coordinates);
    }

    public void saveCoordinates(Coord coordinates) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(latitudeKey, Double.doubleToLongBits(coordinates.getLat()));
        editor.putLong(longitudeKey, Double.doubleToLongBits(coordinates.getLon()));
        editor.apply();
    }

    public Coord loadCoordinates() {
        double latitude = Double.longBitsToDouble(sharedPreferences.getLong(latitudeKey, Double.doubleToLongBits(55.751244)));
        double longitude = Double.longBitsToDouble(sharedPreferences.getLong(longitudeKey, Double.doubleToLongBits(37.618423)));
        Coord coordinates = new Coord();
        coordinates.setLat(latitude);
        coordinates.setLon(longitude);
        return coordinates;
    }
}
