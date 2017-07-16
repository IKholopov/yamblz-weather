package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.weather.common.Coord;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    private WeatherInteractor interactor;

    public WeatherFragmentPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
    }

    public void updateCurrentWeather(Coord coordinates) {
        interactor.getCurrentWeather(coordinates)
                .subscribe(currentWeather -> getView().showCurrentWeather(currentWeather));
        getView().showTenperatureMetric(interactor.getTemperaturMertric());
    }
}
