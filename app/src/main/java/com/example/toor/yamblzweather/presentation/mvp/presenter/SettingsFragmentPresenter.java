package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;

import javax.inject.Inject;

public class SettingsFragmentPresenter extends BaseFragmentPresenter<SettingsView> {

    private SettingsInteractor interactor;

    @Inject
    WeatherScheduleJob scheduleJob;

    @Inject
    public SettingsFragmentPresenter(SettingsInteractor interactor) {
        this.interactor = interactor;

        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void onAttach(SettingsView view) {
        super.onAttach(view);
    }

    public void showSettings() {
        if (getClass() != null) {
            unSubcribeOnDetach(interactor.getUserSettings().subscribe((settings, throwable) -> getView().setSettings(settings)));
            unSubcribeOnDetach(getView().getSelectedCityObservable().subscribe(
                    (input) -> interactor.getAutocomplete(input.toString()).subscribe(
                            (places) -> getView().updateCitiesSuggestionList(places)
                    )
            ));
        }
    }

    public void saveTemperatureMetric(TemperatureMetric metric) {
        interactor.saveTemperatureMetric(metric);
    }

    public void saveUpdateInterval(long interval) {
        interactor.saveUpdateInterval(interval);

        scheduleJob.startJob();
    }
}
