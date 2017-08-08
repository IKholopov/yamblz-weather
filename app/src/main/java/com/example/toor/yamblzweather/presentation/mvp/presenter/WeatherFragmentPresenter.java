package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.scopes.ActivityScope;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.WeatherPagerFragment;

import javax.inject.Inject;
import javax.inject.Scope;

import io.reactivex.Single;

@ActivityScope
public class WeatherFragmentPresenter extends BaseFragmentPresenter<WeatherView> {

    private WeatherInteractor weatherInteractor;
    private SettingsInteractor settingsInteractor;

    @Inject
    WeatherPagerPresenter pagerPresenter;

    @Inject
    public WeatherFragmentPresenter(WeatherInteractor weatherInteractor, SettingsInteractor settingsInteractor) {
        this.weatherInteractor = weatherInteractor;
        this.settingsInteractor = settingsInteractor;
    }

    public Single<PlaceModel> getPlace(int position) {
        return pagerPresenter.getPlaces().map(list -> {
            if(position < list.size()) {
                return list.get(position);
            }
            return null;
        });
    }

    public void getWeather(PlaceModel place, WeatherView view) {
        if (view == null)
            return;
        unSubcribeOnDetach(weatherInteractor.getWeatherFromDB(place).subscribe((weather, throwable)
                -> {
            if (throwable != null) {
                view.showErrorFragment();
                return;
            }
            view.showCurrentWeather(weather, place.getName());
        }));
    }

    public void updateWeather(PlaceModel place,  WeatherView view) {
        if ( view == null)
            return;
        unSubcribeOnDetach(weatherInteractor.updateWeather(place).subscribe((weather, throwable)
                -> {
            if (throwable != null) {
                view.showErrorFragment();
                return;
            }
            view.showCurrentWeather(weather, place.getName());
        }));
    }

    public
    @NonNull
    Single<TemperatureMetric> getMetric() {
        return settingsInteractor.getUserSettings().map(SettingsModel::getMetric);
    }
}
