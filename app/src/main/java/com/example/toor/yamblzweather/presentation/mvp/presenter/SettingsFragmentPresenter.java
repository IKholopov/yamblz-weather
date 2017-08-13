package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.util.Log;

import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SettingsFragmentPresenter extends BaseFragmentPresenter<SettingsView> {

    private static final String TAG = "SettingsPresenter";

    private SettingsInteractor interactor;

    @Inject
    WeatherScheduleJob scheduleJob;

    @Inject
    public SettingsFragmentPresenter(SettingsInteractor interactor) {
        this.interactor = interactor;

        App.getInstance().getAppComponent().inject(this);
    }

    public void showSettings() {
        if (getClass() != null) {
            unSubcribeOnDetach(interactor.getUserSettings()
                    .subscribe((settings, throwable) -> getView().setSettings(settings)));
        }
    }

    /*public Single<PlaceModel> fetchAndSaveCityDetails(PlaceModel placeModel) {
        Single<PlaceModel> request = interactor.getPlaceDetails(placeModel.getPlaceId());
        request.subscribe(
                placeDetails -> {
                    interactor.saveSelectedCity(placeDetails);
                },
                error -> Log.e(TAG, "Failed to fetch city details")
        );
        return request.timeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    public void saveTemperatureMetric(TemperatureMetric metric) {
        interactor.saveTemperatureMetric(metric);
    }

    public void saveUpdateInterval(long interval) {
        interactor.saveUpdateInterval(interval);

        scheduleJob.startJob();
    }

    /*public void subscribeOnCityNameChanges(Observable<CharSequence> observable) {
        unSubcribeOnDetach(observable.throttleLast(AUTOCOMPLETE_CALLDOWN, TimeUnit.MILLISECONDS).subscribe(
                input -> interactor.getAutocomplete(input.toString()).subscribe(
                        places -> {
                            SettingsView view = getView();
                            if (view != null) {
                                view.updateCitiesSuggestionList(places);
                            }
                        },
                        error -> {
                            Log.e(TAG, "Failed to fetch place suggestions");
                        }
                )
        ));
    }*/
}
