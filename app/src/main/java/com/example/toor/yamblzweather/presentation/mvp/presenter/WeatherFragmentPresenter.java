package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.content.Context;

import com.example.toor.yamblzweather.data.repositories.weather.common.Coord;
import com.example.toor.yamblzweather.data.repositories.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import javax.inject.Inject;

public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    private WeatherInteractor interactor;

    @Inject
    Context context;

    public WeatherFragmentPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void inject() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    @Override
    public void onAttach(WeatherView view) {
        super.onAttach(view);
    }

    public void updateCurrentWeather(Coord coordinates) {
        if (NetworkConectionChecker.isNetworkAvailable(context))
            interactor.getCurrentWeather(coordinates)
                    .subscribe(currentWeather -> getView().showCurrentWeather(currentWeather, interactor.getTemperatureMertric()));
        else {
            try {
                CurrentWeather currentWeather = interactor.loadCurrentWeatherFromCache();
                getView().showCurrentWeather(currentWeather, interactor.getTemperatureMertric());
            } catch (Exception e) {
                getView().showErrorFragment();
            }
        }
    }
}
