package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.data.repositories.settings.Settings;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface SettingsView extends BaseView {

    void setSettings(Settings settings);
}
