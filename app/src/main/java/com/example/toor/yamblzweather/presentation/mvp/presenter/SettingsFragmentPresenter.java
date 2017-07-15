package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.utils.OWSupportedUnits;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;

import static com.example.toor.yamblzweather.domain.utils.OWSupportedUnits.CELSIUS;

public class SettingsFragmentPresenter extends BaseFragmentPresenter<SettingsView> {

    private SettingsInteractor interactor;

    public SettingsFragmentPresenter(SettingsInteractor interactor) {
        this.interactor = interactor;
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
    }

    public void saveTemperatureState(boolean state) {
        interactor.saveTemperatureMetric(state);
    }
}
