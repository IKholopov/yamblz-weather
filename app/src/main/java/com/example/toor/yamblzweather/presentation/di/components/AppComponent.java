package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.data.repositories.info.InfoRepositoryImpl;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepositoryImpl;
import com.example.toor.yamblzweather.presentation.di.modules.ActivityModule;
import com.example.toor.yamblzweather.presentation.di.modules.AppModule;
import com.example.toor.yamblzweather.presentation.di.modules.DataModule;
import com.example.toor.yamblzweather.presentation.di.modules.UtilsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, UtilsModule.class})
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);

    void inject(WeatherRepositoryImpl repository);

    void inject(InfoRepositoryImpl repository);
}
