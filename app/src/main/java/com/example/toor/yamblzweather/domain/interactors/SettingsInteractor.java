package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.domain.providers.SettingsProvider;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

import javax.inject.Inject;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.CELSIUS;
import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.FAHRENHEIT;

public class SettingsInteractor {

    @Inject
    SettingsProvider provider;

    public SettingsInteractor(SettingsProvider provider) {
        this.provider = provider;

        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    public Settings getUserSettings() {
        return provider.loadSettings();
    }

    public void saveTemperatureMetric(boolean type) {
        provider.saveTemperatureMetric(type ? CELSIUS : FAHRENHEIT);
    }

    public void saveUpdateInterval(long interval) {
        provider.saveUpdateWeatherInterval(interval);
    }

}
