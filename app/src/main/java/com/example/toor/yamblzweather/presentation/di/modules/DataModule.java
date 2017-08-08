package com.example.toor.yamblzweather.presentation.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.toor.yamblzweather.data.database.CupboardDB;
import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.settings.SettingsPreference;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.data.network.PlacesService;
import com.example.toor.yamblzweather.data.repositories.info.InfoRepository;
import com.example.toor.yamblzweather.data.repositories.info.InfoRepositoryImpl;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepository;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepositoryImpl;
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
    WeatherRepository provideWeatherRepository(DataBase dataBase, OWService service) {
        return new WeatherRepositoryImpl(dataBase, service);
    }

    @Provides
    @Singleton
    PlacesRepository providePlacesRepository(DataBase dataBase, PlacesService service) {
        return new PlacesRepositoryImpl(dataBase, service);
    }

    @Provides
    @Singleton
    SettingsRepository provideSettingsRepository(SettingsPreference preference, PlacesService service) {
        return new SettingsRepositoryImpl(preference, service);
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
        return new CupboardDB(context);
    }
}
