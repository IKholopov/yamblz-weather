package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.model.weather.gson.common.Coord;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import javax.inject.Inject;

public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    @Inject
    WeatherInteractor interactor;

    public WeatherFragmentPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
    }

    public void updateCurrentWeather(Coord coordinates) {
        interactor.getCurrentWeather(coordinates)
                .subscribe(currentWeather ->getView().showCurrentWeather(currentWeather));
    }
}
