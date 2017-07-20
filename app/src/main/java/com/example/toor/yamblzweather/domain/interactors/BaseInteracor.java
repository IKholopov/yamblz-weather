package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.models.settings.SettingsPreference;

public abstract class BaseInteracor {

//    @Inject
//    SettingsPreference preference;

    public BaseInteracor() {
        inject();
    }

    protected abstract void inject();
}
