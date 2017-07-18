package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.domain.interactors.InfoInteractor;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.service.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.presentation.di.modules.ScreenModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;
import com.example.toor.yamblzweather.presentation.mvp.presenter.ConnectionErrorFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;

import dagger.Subcomponent;

@WeatherScope
@Subcomponent(modules = WeatherModule.class)
public interface WeatherComponent {

    void inject(WeatherFragmentPresenter presenter);

    void inject(SettingsFragmentPresenter presenter);

    void inject(ConnectionErrorFragmentPresenter presenter);

    void inject(SettingsInteractor interacor);

    void inject(WeatherInteractor interacor);

    void inject(InfoInteractor interactor);

    void inject(WeatherScheduleJob weatherScheduleJob);

    ScreenComponent plus(ScreenModule screenModule);
}
