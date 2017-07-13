package com.example.toor.yamblzweather.presentation.di.modules;

import com.example.toor.yamblzweather.presentation.di.scopes.ScreenScope;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsScreenModule {

    @ScreenScope
    @Provides
    public SettingsFragmentPresenter provideSettingsFragmentPresenter() {
        return new SettingsFragmentPresenterImpl();
    }

}
