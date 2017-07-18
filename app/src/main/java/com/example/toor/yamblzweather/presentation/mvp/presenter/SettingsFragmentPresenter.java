package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.service.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.domain.utils.OWSupportedMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;

import javax.inject.Inject;

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
        getView().setSettings(settings);
    }

    public void saveTemperatureMetric(OWSupportedMetric metric) {
        interactor.saveTemperatureMetric(metric);
    }

    public void saveUpdateInterval(long interval) {
        interactor.saveUpdateInterval(interval);
        weatherScheduleJob.startJob(interactor.getUserSettings().getCoordinates());
    }
}
