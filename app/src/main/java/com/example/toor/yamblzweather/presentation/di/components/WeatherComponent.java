package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.presentation.di.modules.SettingsScreenModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherScreenModule;
import com.example.toor.yamblzweather.presentation.di.scopes.WeatherScope;

import dagger.Subcomponent;

@WeatherScope
@Subcomponent(modules = WeatherModule.class)
public interface WeatherComponent {

    WeatherScreenComponent plus(WeatherScreenModule weatherScreenModule);
    SettingsScreenComponent plus(SettingsScreenModule settingsScreenModule);
}
