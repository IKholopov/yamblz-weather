package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface SettingsView extends BaseView {

    void setTemperatureState(boolean state);

    void setUpdateInterval(long interval);
}
