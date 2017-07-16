package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.data.settings.SettingsPreference;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

import javax.inject.Inject;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.FAHRENHEIT;

public class SettingsInteractor {

    @Inject
    SettingsPreference preference;

    public SettingsInteractor() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    public Settings getUserSettings() {
        return preference.loadUserSettings();
    }

    public void saveTemperatureMetric(boolean type) {
        preference.saveTemperatureMetric(type ? CELSIUS : FAHRENHEIT);
    }

    public void saveUpdateInterval(long interval) {
        preference.saveUpdateWeatherInterval(interval);
    }

}
