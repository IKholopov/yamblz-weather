package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

public class WeatherFragmentPresenterImpl implements WeatherFragmentPresenter {

    private WeatherView view;
    private WeatherInteractor interactor;

    public WeatherFragmentPresenterImpl(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void updateCurrentWeather() {
        view.showCurrentWeather(interactor.loadWeather());
    }

    @Override
    public void setModel() {

    }

    @Override
    public void bindView(WeatherView weatherView) {
        this.view = weatherView;
    }
}
