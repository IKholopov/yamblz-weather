package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.providers.CurrentWeatherProvider;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import dagger.Module;
import dagger.Provides;

import static com.example.toor.yamblzweather.domain.api.ApiKeys.OPEN_WEATHER_MAP_API_KEY;

@Module
public class WeatherModule {

    @Provides
    @WeatherScope
    public WeatherInteractor provideWeatherInteractor(CurrentWeatherProvider provider) {
        return new WeatherInteractor(provider);
    }

    @Provides
    @WeatherScope
    public CurrentWeatherProvider provideCurrentWeatherProvider(OWService service) {
        return new CurrentWeatherProvider(service);
    }

    @Provides
    @WeatherScope
    public OWService provideOwService() {
        return new OWService(OPEN_WEATHER_MAP_API_KEY);
    }
}
