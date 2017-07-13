package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.icu.util.Calendar;

import com.example.toor.yamblzweather.data.SettingsPreference;
import com.example.toor.yamblzweather.data.WeatherRepository.WeatherRepository;
import com.example.toor.yamblzweather.domain.providers.WeatherProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    public SettingsPreference provideSettingsPreference(Context context) {
        return new SettingsPreference(context);
    }

    @Provides
    @Singleton
    public WeatherProvider provideWeatherProvider(Context context, WeatherRepository weatherRepository) {
        return new WeatherProvider(context, weatherRepository);
    }

    @Provides
    @Singleton
    public WeatherRepository provideWeatherRepository() {
        return new WeatherRepository.WeatherBuilder("tambov"
                , System.currentTimeMillis()
                , "Cloudy", 20).build();
    }
}
