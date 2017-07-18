package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.presentation.di.modules.ScreenModule;
import com.example.toor.yamblzweather.presentation.di.scopes.ScreenScope;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.ConnectionConnectionErrorFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.InfoFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.SettingsFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherFragment;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = ScreenModule.class)
public interface ScreenComponent {

    void inject(WeatherFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(InfoFragment fragment);

    void inject(ConnectionConnectionErrorFragment fragment);
}
