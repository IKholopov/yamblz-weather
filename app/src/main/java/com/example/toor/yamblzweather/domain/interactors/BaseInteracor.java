package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.settings.SettingsPreference;

import javax.inject.Inject;

public abstract class BaseInteracor {

    @Inject
    SettingsPreference preference;

    public BaseInteracor() {
        inject();
    }

    protected abstract void inject();
}
