package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import javax.inject.Inject;

public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    private WeatherInteractor weatherInteractor;
    private SettingsInteractor settingsInteractor;

    @Inject
    public WeatherFragmentPresenter(WeatherInteractor weatherInteractor, SettingsInteractor settingsInteractor) {
        this.weatherInteractor = weatherInteractor;
        this.settingsInteractor = settingsInteractor;
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
    }

    public void updateCurrentWeather() {
//        unSubcribeOnDetach(interactor.getFullWeather(city).onErrorResumeNext(поход в базу )
//                .subscribe((fullWeatherModel, throwable) -> {getView().showCurrentWeather(fullWeatherModel));
//                if (throwable != null)});
        unSubcribeOnDetach(settingsInteractor.getUserSettings().subscribe((settingsModel, throwable)
                -> weatherInteractor.getFullWeather(settingsModel.getSelectedCityCoords()).subscribe((fullWeatherModel, throwable1)
                -> getView().showCurrentWeather(fullWeatherModel))));
    }

    public void saveSelectedCity(Coord coord) {
        settingsInteractor.saveSelectedCity(coord);
    }
}
