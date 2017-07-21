package com.example.toor.yamblzweather.data.repositories.settings;

import com.example.toor.yamblzweather.data.models.settings.SettingsPreference;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import io.reactivex.Single;

public class SettingsRepositoryImpl implements SettingsRepository {

    SettingsPreference preference;

    public SettingsRepositoryImpl(SettingsPreference preference) {
        this.preference = preference;
    }

    @Override
    public Single<SettingsModel> getUserSettings() {
        return preference.loadUserSettings();
    }

    @Override
    public void saveTemperatureMetric(TemperatureMetric metric) {
        preference.saveTemperatureMetric(metric);
    }

    @Override
    public void saveUpdateInterval(long interval) {
        preference.saveUpdateWeatherInterval(interval);
    }

    @Override
    public void saveSelectedCity(int cityId) {
        preference.saveSelectedCityId(cityId);
    }

}
