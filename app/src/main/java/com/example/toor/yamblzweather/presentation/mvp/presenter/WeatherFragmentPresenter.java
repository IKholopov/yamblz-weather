package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

public interface WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    void updateCurrentWether();
}
