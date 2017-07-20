package com.example.toor.yamblzweather.data.models.settings;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import io.reactivex.Single;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.fromString;

public class SettingsPreference {

    private static final String TEMPERATURE_KEY = "temperature_key";
    private static final String INTERVAL_KEY = "interval_key";
    private static final String LATITUDE_KEY = "latitude_key";
    private static final String LONGITUDE_KEY = "longitude_key";
    private static final String CURRENT_WEATHER_KEY = "current_weather_key";

    private static final long MIN_UPDATE_INTERVAL = 1 * 60 * 60 * 1000; // interval is 1 hour

    private SharedPreferences sharedPreferences;

    public SettingsPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveTemperatureMetric(TemperatureMetric metric) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEMPERATURE_KEY, metric.getUnit());
        editor.apply();
    }

    public TemperatureMetric loadTemperatureMetric() {
        String metric = sharedPreferences.getString(TEMPERATURE_KEY, CELSIUS.getUnit());
        return fromString(metric);
    }

    public void saveUpdateWeatherInterval(long interval) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(INTERVAL_KEY, interval);
        editor.apply();
    }

    public long loadUpdateWeatherInterval() {
        return sharedPreferences.getLong(INTERVAL_KEY, MIN_UPDATE_INTERVAL);
    }

    public Single<SettingsModel> loadUserSettings() {
        TemperatureMetric metric = loadTemperatureMetric();
        long interval = loadUpdateWeatherInterval();
        return Single.fromCallable(() -> new SettingsModel(metric, interval));
    }

    public void saveCoordinates(Coord coordinates) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LATITUDE_KEY, Double.doubleToLongBits(coordinates.getLat()));
        editor.putLong(LONGITUDE_KEY, Double.doubleToLongBits(coordinates.getLon()));
        editor.apply();
    }

    public Coord loadCoordinates() {
        double latitude = Double.longBitsToDouble(sharedPreferences.getLong(LATITUDE_KEY, Double.doubleToLongBits(55.751244)));
        double longitude = Double.longBitsToDouble(sharedPreferences.getLong(LONGITUDE_KEY, Double.doubleToLongBits(37.618423)));
        Coord coordinates = new Coord();
        coordinates.setLat(latitude);
        coordinates.setLon(longitude);
        return coordinates;
    }

    public void saveCurrentWeather(String currentWeather) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_WEATHER_KEY, currentWeather);
        editor.apply();
    }

    public
    @Nullable
    String loadCurrentWeather() {
        return sharedPreferences.getString(CURRENT_WEATHER_KEY, null);
    }
}
