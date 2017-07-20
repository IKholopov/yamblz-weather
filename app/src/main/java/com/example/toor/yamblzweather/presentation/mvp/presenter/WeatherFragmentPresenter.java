package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import javax.inject.Inject;

public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    private WeatherInteractor interactor;

    @Inject
    public WeatherFragmentPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void inject() {
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
    }

    public void updateCurrentWeather(City city) {
//        unSubcribeOnDetach(interactor.getFullWeather(city).onErrorResumeNext(поход в базу )
//                .subscribe((fullWeatherModel, throwable) -> {getView().showCurrentWeather(fullWeatherModel));
//                if (throwable != null)});
        unSubcribeOnDetach(interactor.getFullWeather(city)
                .subscribe((fullWeatherModel, throwable) -> getView().showCurrentWeather(fullWeatherModel)));
    }
}
