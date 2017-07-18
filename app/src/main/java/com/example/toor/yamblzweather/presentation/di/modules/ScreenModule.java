package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.domain.interactors.InfoInteractor;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.di.scopes.ScreenScope;
import com.example.toor.yamblzweather.presentation.mvp.presenter.ConnectionErrorFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.presenter.InfoFragmentPresenter;
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

    @ScreenScope
    @Provides
    public InfoFragmentPresenter provideInfoFragmentPresenter(InfoInteractor infoInteractor) {
        return new InfoFragmentPresenter(infoInteractor);
    }

    @ScreenScope
    @Provides
    public ConnectionErrorFragmentPresenter provideConnectionErrorFragmentPresenter() {
        return new ConnectionErrorFragmentPresenter();
    }
}
