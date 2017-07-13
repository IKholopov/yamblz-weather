package com.example.toor.yamblzweather.presentation.presenter;

import com.example.toor.yamblzweather.presentation.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.view.WeatherView;

public interface WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    void updateCurrentWether();
}
