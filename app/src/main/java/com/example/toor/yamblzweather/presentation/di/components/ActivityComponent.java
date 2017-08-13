package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.domain.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.presentation.di.modules.ActivityModule;
import com.example.toor.yamblzweather.presentation.di.scopes.ActivityScope;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.MainActivity;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.ForecastAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.InfoFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.PlacesListFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.SettingsFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherPagerFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(WeatherScheduleJob scheduleJob);

    void inject(WeatherFragment fragment);

    void inject(WeatherPagerFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(PlacesListFragment fragment);

    void inject(InfoFragment fragment);

    void inject(ForecastAdapter adapter);

    void inject(MainActivity activity);
}
