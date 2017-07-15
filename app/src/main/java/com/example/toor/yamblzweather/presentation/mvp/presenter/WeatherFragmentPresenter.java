package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.model.gson.common.Coord;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    @Inject
    WeatherInteractor interactor;

    private Disposable subscription;

    public WeatherFragmentPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
    }

    public void updateCurrentWeather(Coord coordinates) {
        subscription = interactor.getCurrentWeather(coordinates)
                .subscribe(currentWeather ->getView().showCurrentWeather(currentWeather));
    }
}
