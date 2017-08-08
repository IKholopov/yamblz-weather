package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import javax.inject.Inject;
import javax.inject.Scope;

import io.reactivex.Single;

public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    private WeatherInteractor weatherInteractor;
    private SettingsInteractor settingsInteractor;
    private PlaceModel place;

    @Inject
    public WeatherFragmentPresenter(WeatherInteractor weatherInteractor, SettingsInteractor settingsInteractor) {
        this.weatherInteractor = weatherInteractor;
        this.settingsInteractor = settingsInteractor;
    }

    public void setPlace(@NonNull PlaceModel placeModel) {
        this.place = placeModel;
    }

    public void getWeather() {
        if (getView() == null)
            return;
        unSubcribeOnDetach(weatherInteractor.getWeatherFromDB(place).subscribe((weather, throwable)
                -> {
            if (throwable != null) {
                getView().showErrorFragment();
                return;
            }
            getView().showCurrentWeather(weather, place.getName());
        }));
    }

    public void onAttach(WeatherView view, PlaceModel place) {
        this.place = place;
        super.onAttach(view);
    }

    public void updateWeather() {
        if (getView() == null)
            return;
        unSubcribeOnDetach(weatherInteractor.updateWeather(place).subscribe((weather, throwable)
                -> {
            if (throwable != null) {
                getView().showErrorFragment();
                return;
            }
            getView().showCurrentWeather(weather, place.getName());
        }));
    }

    public
    @NonNull
    Single<TemperatureMetric> getMetric() {
        return settingsInteractor.getUserSettings().map(SettingsModel::getMetric);
    }
}
