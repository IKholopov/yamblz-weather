package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.service.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;

import javax.inject.Inject;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.CELSIUS;

public class SettingsFragmentPresenter extends BaseFragmentPresenter<SettingsView> {

    private SettingsInteractor interactor;

    @Inject
    WeatherScheduleJob weatherScheduleJob;

    public SettingsFragmentPresenter(SettingsInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void inject() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    @Override
    public void onAttach(SettingsView view) {
        super.onAttach(view);
    }

    public void showSettings() {
        Settings settings = interactor.getUserSettings();
        showTemperatureMetric(settings.getMetric());
        showUpdateWeatherInterval(settings.getUpdateWeatherInterval());
    }

    private void showTemperatureMetric(OWSupportedUnits metric) {
        if (metric == CELSIUS)
            getView().setTemperatureState(true);
        else
            getView().setTemperatureState(false);
    }

    private void showUpdateWeatherInterval(long interval) {
        interactor.saveUpdateInterval(interval);
        getView().setUpdateInterval(interval);
    }

    public void saveTemperatureState(boolean state) {
        interactor.saveTemperatureMetric(state);
    }

    public void saveUpdateInterval(long interval) {
        interactor.saveUpdateInterval(interval);
        weatherScheduleJob.startJob(interactor.getUserSettings().getCoordinates());
    }
}
