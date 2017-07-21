package com.example.toor.yamblzweather.data.repositories.settings;

import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import io.reactivex.Single;

public interface SettingsRepository {

    Single<SettingsModel> getUserSettings();

    void saveTemperatureMetric(TemperatureMetric metric);

    void saveUpdateInterval(long interval);

    void saveSelectedCity(int cityId);
}
