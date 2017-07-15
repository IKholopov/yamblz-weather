package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.data.model.settings.SettingsPreference;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.di.scopes.ScreenScope;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ScreenModule {

    @ScreenScope
    @Provides
    public WeatherFragmentPresenter provideWeatherFragmentPresenter(WeatherInteractor interactor) {
        return new WeatherFragmentPresenter(interactor);
    }

    @ScreenScope
    @Provides
    public SettingsFragmentPresenter provideSettingsFragmentPresenter(SettingsInteractor interactor) {
        return new SettingsFragmentPresenter(interactor);
    }
}
