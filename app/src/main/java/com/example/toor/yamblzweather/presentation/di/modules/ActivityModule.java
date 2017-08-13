package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.data.repositories.info.InfoRepository;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepository;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.domain.interactors.InfoInteractor;
import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.presentation.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    @ActivityScope
    public WeatherInteractor provideWeatherInteractor(WeatherRepository repository) {
        return new WeatherInteractor(repository);
    }

    @Provides
    @ActivityScope
    public SettingsInteractor provideSettingsInteractor(SettingsRepository repository) {
        return new SettingsInteractor(repository);
    }

    @Provides
    @ActivityScope
    public InfoInteractor provideInfoInteractor(InfoRepository repository) {
        return new InfoInteractor(repository);
    }    

    @Provides
    @ActivityScope
    public PlacesInteractor providePlacesInteractor(WeatherRepository weatherRepository,
                                                    PlacesRepository placesRepository) {
        return new PlacesInteractor(weatherRepository, placesRepository);
    }

    @Provides
    @ActivityScope
    public WeatherScheduleJob provideWeatherScheduleJob() {
        return new WeatherScheduleJob();
    }
}
