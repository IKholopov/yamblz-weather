package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;
import com.example.toor.yamblzweather.data.models.weather.five_day.WeatherForecastElement;
import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetricConverter;
import com.example.toor.yamblzweather.presentation.di.scopes.ActivityScope;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

import static com.example.toor.yamblzweather.domain.utils.TemperatureMetric.CELSIUS;

/**
 * Created by igor on 8/6/17.
 */

@ActivityScope
public class WeatherPresenter extends BaseFragmentPresenter<WeatherView> {
    private static final String TAG = "WeatherPresenter";

    @Inject  Context context;

    private PlacesInteractor placesInteractor;
    private WeatherInteractor weatherInteractor;
    private SettingsInteractor settingsInteractor;

    PublishSubject<List<PlaceModel>> places = PublishSubject.create();

    @Inject
    public WeatherPresenter(PlacesInteractor placesInteractor, WeatherInteractor weatherInteractor,
                            SettingsInteractor settingsInteractor) {
        this.placesInteractor = placesInteractor;
        this.weatherInteractor = weatherInteractor;
        this.settingsInteractor = settingsInteractor;
    }

    public Single<List<PlaceModel>> getPlaces() {
        return placesInteractor.getAllPlaces().toList().observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<PlaceModel>> observePlaces() {
        return places;
    }

    public void updatePlacesList() {
        placesInteractor.getAllPlaces().toList().observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> places.onNext(list));
    }

    public Single<PlaceModel> getPlace(int position) {
        return getPlaces().map(list -> {
            if(position < list.size()) {
                return list.get(position);
            }
            return null;
        });
    }

    public Single<String> getCurrentTemperatureString(@NonNull DailyForecastElement weather) {
        return getMetric().map(metric -> {
            double temperature = weather.getTemp().getDay();
            String metricStr = convertMetricToString(metric);
            int temperatureRound = TemperatureMetricConverter.getSupportedTemperature(temperature, metric);
            String temperatureStr = String.valueOf(temperatureRound);
            return temperatureStr + " " + metricStr;
        });
    }

    private String convertMetricToString(TemperatureMetric metric) {
        if (metric == CELSIUS)
            return context.getString(R.string.celsius);
        else
            return context.getString(R.string.fahrenheit);
    }

    public void getWeather(PlaceModel place, WeatherView view) {
        if (view == null)
            return;
        unSubcribeOnDetach(weatherInteractor.getWeatherFromDB(place)
                .observeOn(AndroidSchedulers.mainThread()).subscribe((weather, throwable)
                -> {
            if (throwable != null) {
                view.showErrorFragment();
                return;
            }
            view.showWeather(weather, place.getName());
        }));
    }

    public void updateWeather(PlaceModel place,  WeatherView view) {
        if ( view == null)
            return;
        unSubcribeOnDetach(weatherInteractor.updateWeather(place).observeOn(AndroidSchedulers.mainThread())
                .subscribe((weather, throwable)
                -> {
            if (throwable != null) {
                view.showErrorFragment();
                return;
            }
            view.showWeather(weather, place.getName());
        }));
    }

    public
    @NonNull
    Single<TemperatureMetric> getMetric() {
        return settingsInteractor.getUserSettings().map(SettingsModel::getMetric);
    }
}