package com.example.toor.yamblzweather.data.models.settings;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import io.reactivex.Single;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.fromString;

public class SettingsPreference {

    public static final String TEMPERATURE_KEY = "temperature_key";
    public static final String INTERVAL_KEY = "interval_key";
    public static final String CITY_ID = "city_id";
    public static final String CITY_LAT = "city_lat";
    public static final String CITY_LON = "city_lon";
    public static final String CITY_NAME = "city_name";
    public static final String CACHE_TIME = "cache_time";

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

    public
    @NonNull
    TemperatureMetric loadTemperatureMetric() {
        String metric = sharedPreferences.getString(TEMPERATURE_KEY, CELSIUS.getUnit());
        return fromString(metric);
    }

    public void saveUpdateWeatherInterval(long interval) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(INTERVAL_KEY, interval);
        editor.apply();
    }

    public
    @NonNull
    long loadUpdateWeatherInterval() {
        return sharedPreferences.getLong(INTERVAL_KEY, MIN_UPDATE_INTERVAL);
    }

    public
    @NonNull
        Single<SettingsModel> loadUserSettings() {
        TemperatureMetric metric = loadTemperatureMetric();
        long interval = loadUpdateWeatherInterval();
        int cityId = loadSelectedCityId();
        String cityName = loadSelectedCityName();
        Coord coords = loadSelectedCityCoords();
        return Single.fromCallable(() -> new SettingsModel.Builder(metric, interval)
                .cityId(cityId).coords(coords).cityName(cityName).build());
    }

    public void saveSelectedCityId(int cityId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CITY_ID, cityId);
        editor.apply();
    }

    public void saveSelectedCityCoords(double lat, double lon) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(CITY_LAT, Double.valueOf(lat).floatValue());
        editor.putFloat(CITY_LON, Double.valueOf(lon).floatValue());
        editor.apply();
    }

    public void saveSelectedCityName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CITY_NAME, name);
        editor.apply();
    }

    public
    @NonNull
    int loadSelectedCityId() {
        return sharedPreferences.getInt(CITY_ID, 524901);
    }

    public
    @NonNull
    Coord loadSelectedCityCoords() {
        Coord coords = new Coord(sharedPreferences.getFloat(CITY_LAT, 55.754f),
                sharedPreferences.getFloat(CITY_LON, 37.615f));
        return coords;
    }

    public
    @NonNull
    String loadSelectedCityName() {
        return sharedPreferences.getString(CITY_NAME, "Moscow");
    }

    public void saveCacheTime(long time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(CACHE_TIME, time);
        editor.apply();
    }

    public
    @NonNull
    int loadCacheTime() {
        return sharedPreferences.getInt(CACHE_TIME, 0);
    }
}
