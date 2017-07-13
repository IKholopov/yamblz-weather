package com.example.toor.yamblzweather.presentation.presenter;

import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.view.WeatherView;

public class WeatherFragmentPresenterImpl implements WeatherFragmentPresenter {

    private WeatherView view;
    private WeatherInteractor interactor;

    public WeatherFragmentPresenterImpl(WeatherInteractor interactor) {
        this.interactor = interactor;
    }


    @Override
    public void updateCurrentWether() {

    }

    @Override
    public void setModel() {

    }

    @Override
    public void bindView(WeatherView weatherView) {
        this.view = weatherView;
    }
}
