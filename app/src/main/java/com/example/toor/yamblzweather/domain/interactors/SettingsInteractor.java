package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.repositories.settings.Settings;
import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

public class SettingsInteractor extends BaseInteracor {

    public Settings getUserSettings() {
        return preference.loadUserSettings();
    }

    public void saveTemperatureMetric(OWSupportedMetric metric) {
        preference.saveTemperatureMetric(metric);
    }

    public void saveUpdateInterval(long interval) {
        preference.saveUpdateWeatherInterval(interval);
    }

    @Override
    protected void inject() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }
}
