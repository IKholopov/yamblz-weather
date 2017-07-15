package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.toor.yamblzweather.data.model.settings.SettingsPreference;
import com.example.toor.yamblzweather.domain.service.OWService;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.toor.yamblzweather.domain.api.ApiKeys.OPEN_WEATHER_MAP_API_KEY;

@Module
public class DataModule {

    @Provides
    @Singleton
    public SettingsPreference provideSettingsPreference(SharedPreferences preferences) {
        return new SettingsPreference(preferences);
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    public OWService provideOwService() {
        return new OWService(OPEN_WEATHER_MAP_API_KEY);
    }
}
