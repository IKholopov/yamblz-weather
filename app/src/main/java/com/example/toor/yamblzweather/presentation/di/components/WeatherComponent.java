package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.di.modules.ScreenModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import dagger.Subcomponent;

@WeatherScope
@Subcomponent(modules = WeatherModule.class)
public interface WeatherComponent {

    void inject(WeatherInteractor interactor);
    void inject(SettingsInteractor interactor);

    ScreenComponent plus(ScreenModule screenModule);
}
