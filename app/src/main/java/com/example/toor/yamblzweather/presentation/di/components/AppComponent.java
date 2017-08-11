package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.data.repositories.info.InfoRepositoryImpl;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepository;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepositoryImpl;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepositoryImpl;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.ActivityModule;
import com.example.toor.yamblzweather.presentation.di.modules.AppModule;
import com.example.toor.yamblzweather.presentation.di.modules.DataModule;
import com.example.toor.yamblzweather.presentation.di.modules.UtilsModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.ForecastAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, UtilsModule.class})
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);

    void inject(WeatherRepositoryImpl repository);

    void inject(PlacesRepositoryImpl repository);

    void inject(InfoRepositoryImpl repository);

    void inject(SettingsFragmentPresenter presenter);

    void inject(App app);
}
