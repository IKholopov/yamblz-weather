package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.data.settings.SettingsPreference;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.providers.SettingsProvider;
import com.example.toor.yamblzweather.domain.providers.WeatherProvider;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    @WeatherScope
    public WeatherInteractor provideWeatherInteractor(WeatherProvider provider) {
        return new WeatherInteractor(provider);
    }

    @Provides
    @WeatherScope
    public WeatherProvider provideWeatherProvider(OWService service) {
        return new WeatherProvider(service);
    }

    @Provides
    @WeatherScope
    public SettingsInteractor provideSettingsInteractor(SettingsProvider provider) {
        return new SettingsInteractor(provider);
    }

    @Provides
    @WeatherScope
    public SettingsProvider provideSettingsProvide(SettingsPreference preference) {
        return new SettingsProvider(preference);
    }
}
