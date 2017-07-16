package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.service.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    @WeatherScope
    public WeatherInteractor provideWeatherInteractor() {
        return new WeatherInteractor();
    }

    @Provides
    @WeatherScope
    public SettingsInteractor provideSettingsInteractor() {
        return new SettingsInteractor();
    }

}
