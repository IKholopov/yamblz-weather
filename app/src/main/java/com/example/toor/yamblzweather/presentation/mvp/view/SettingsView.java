package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public interface SettingsView extends BaseView {

    void setTemperatureMetric(OWSupportedMetric metric);

    void setUpdateInterval(long interval);
}
