package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.domain.interactors.InfoInteractor;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    @WeatherScope
    public WeatherInteractor provideWeatherInteractor(WeatherRepository repository, SettingsRepository settingsRepository) {
        return new WeatherInteractor(repository, settingsRepository);
    }

    @Provides
    @WeatherScope
    public SettingsInteractor provideSettingsInteractor(SettingsRepository repository) {
        return new SettingsInteractor(repository);
    }

    @Provides
    @WeatherScope
    public InfoInteractor provideInfoInteractor() {
        return new InfoInteractor();
    }

}
