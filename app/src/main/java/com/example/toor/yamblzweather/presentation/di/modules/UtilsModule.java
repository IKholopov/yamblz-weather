package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.os.Build;

import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.domain.service.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.toor.yamblzweather.domain.api.ApiKeys.OPEN_WEATHER_MAP_API_KEY;

@Module
public class UtilsModule {

    @Singleton
    @Provides
    public Locale provideLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    @Provides
    @Singleton
    public OWService provideOwService() {
        return new OWService(OPEN_WEATHER_MAP_API_KEY);
    }

    @Provides
    @Singleton
    public WeatherScheduleJob provideWeatherScheduleJob() {
        return new WeatherScheduleJob();
    }

}
