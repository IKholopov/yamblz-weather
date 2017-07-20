package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.settings.SettingsPreference;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.data.repositories.info.InfoRepository;
import com.example.toor.yamblzweather.data.repositories.info.InfoRepositoryImpl;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepositoryImpl;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(DataBase dataBase, OWService service, SettingsPreference preference) {
        return new WeatherRepositoryImpl(dataBase, service, preference);
    }

    @Provides
    @Singleton
    SettingsRepository provideSettingsRepository(SettingsPreference preference) {
        return new SettingsRepositoryImpl(preference);
    }

    @Provides
    @Singleton
    InfoRepository provideInfoRepository() {
        return new InfoRepositoryImpl();
    }

    @Provides
    @Singleton
    SettingsPreference provideSettingsPreference(SharedPreferences preferences) {
        return new SettingsPreference(preferences);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    DataBase provideDataBase(Context context) {
        return new DataBase(context);
    }
}
