package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.presentation.di.modules.WeatherScreenModule;
import com.example.toor.yamblzweather.presentation.di.scopes.ScreenScope;
import com.example.toor.yamblzweather.presentation.view.fragment.WeatherFragment;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = WeatherScreenModule.class)
public interface WeatherScreenComponent {

    void inject(WeatherFragment fragment);
}
