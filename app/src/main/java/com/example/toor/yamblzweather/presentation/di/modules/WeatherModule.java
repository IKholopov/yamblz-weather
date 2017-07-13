package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.providers.WeatherProvider;
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
}
