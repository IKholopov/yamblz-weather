package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.os.Build;

import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.data.network.PlacesService;
import com.example.toor.yamblzweather.domain.scheduler.WeatherScheduleJob;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.toor.yamblzweather.data.network.api.ApiKeys.GOOGLE_PLACES_API_KEY;
import static com.example.toor.yamblzweather.data.network.api.ApiKeys.OPEN_WEATHER_MAP_API_KEY;

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
    public PlacesService providePlacesService() {
        return new PlacesService(GOOGLE_PLACES_API_KEY);
    }

    @Provides
    @Singleton
    public WeatherScheduleJob provideWeatherScheduleJob() {
        return new WeatherScheduleJob();
    }
}
