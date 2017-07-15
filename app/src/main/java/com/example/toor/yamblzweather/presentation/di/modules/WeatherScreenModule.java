package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.di.scopes.ScreenScope;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherScreenModule {

    @ScreenScope
    @Provides
    public WeatherFragmentPresenter provideWeatherFragmentPresenter(WeatherInteractor interactor) {
        return new WeatherFragmentPresenter(interactor);
    }
}
