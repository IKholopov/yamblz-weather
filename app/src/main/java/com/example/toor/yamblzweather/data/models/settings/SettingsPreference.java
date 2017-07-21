package com.example.toor.yamblzweather.data.models.settings;

import android.content.SharedPreferences;

import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import io.reactivex.Single;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.fromString;

public class SettingsPreference {

    private static final String TEMPERATURE_KEY = "temperature_key";
    private static final String INTERVAL_KEY = "interval_key";
    private static final String CITY_ID = "city_id";

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
        int cityId = loadSelectedCityId();
        return Single.fromCallable(() -> new SettingsModel.Builder(metric, interval).cityId(cityId).build());
    }

    public void saveSelectedCityId(int cityId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CITY_ID, cityId);
        editor.apply();
    }

    public int loadSelectedCityId() {
        return sharedPreferences.getInt(CITY_ID, 524901);
    }
}
