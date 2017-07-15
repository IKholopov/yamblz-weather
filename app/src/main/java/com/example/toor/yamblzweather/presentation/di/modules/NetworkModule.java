package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.domain.service.OWService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.toor.yamblzweather.domain.api.ApiKeys.OPEN_WEATHER_MAP_API_KEY;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OWService provideService() {
        return new OWService(OPEN_WEATHER_MAP_API_KEY);
    }
}
