package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.data.settings.Settings;
import com.example.toor.yamblzweather.data.settings.TemperatureType;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;

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
        showTemperatureState(settings.getTemperatureType());
        showUpdateWeatherInterval(settings.getUpdateWeatherInterval());
    }

    private void showTemperatureState(TemperatureType temperatureType) {
        if (temperatureType.ordinal() == 1)
            getView().setTemperatureState(false);
        else
            getView().setTemperatureState(true);
    }

    private void showUpdateWeatherInterval(long interval) {
        interactor.saveUpdateInterval(interval);
    }

    public void saveTemperatureState(boolean state) {
        interactor.saveTemperatureType(state);
    }
}
