package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface SettingsView extends BaseView {

    void setSettings(SettingsModel settingsModel);
}
