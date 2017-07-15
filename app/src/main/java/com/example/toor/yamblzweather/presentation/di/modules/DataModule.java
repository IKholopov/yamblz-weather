package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;

import com.example.toor.yamblzweather.data.SettingsPreference;

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
}
