package com.example.toor.yamblzweather.presentation.di.components;

import com.example.toor.yamblzweather.presentation.di.modules.SettingsScreenModule;
import com.example.toor.yamblzweather.presentation.di.scopes.ScreenScope;
import com.example.toor.yamblzweather.presentation.view.fragment.SettingsFragment;

import dagger.Subcomponent;

@ScreenScope
@Subcomponent(modules = SettingsScreenModule.class)
public interface SettingsScreenComponent {

    void inject(SettingsFragment fragment);
}
