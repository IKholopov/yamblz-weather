package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.toor.yamblzweather.data.repositories.settings.SettingsPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

}
