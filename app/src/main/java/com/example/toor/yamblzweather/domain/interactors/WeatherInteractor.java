package com.example.toor.yamblzweather.domain.interactors;

import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.domain.utils.TimeUtils;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.reactivex.Single;

public class WeatherInteractor {

    private WeatherRepository weatherRepository;

    public WeatherInteractor(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @NonNull
    public Single<DailyWeather> getWeatherFromDB(PlaceModel place) {
        PlaceDetails details = detailsFromModel(place);
        return getExtendedWeather(details)
                .doOnSuccess(weatherModel -> {
                    weatherRepository.saveWeather(weatherModel, details);
                    clearOldRecords();
                });
    }

    @NonNull
    public Single<DailyWeather> updateWeather(PlaceModel place) {
        PlaceDetails details = detailsFromModel(place);
        return updateExtendedWeather(details)
                .doOnSuccess(weatherModel -> {
                    weatherRepository.saveWeather(weatherModel, details);
                    clearOldRecords();
                });
    }

    @NonNull
    private Single<DailyWeather> getExtendedWeather(PlaceDetails placeDetails) {
        return weatherRepository.getExtendedWeatherFromDB(placeDetails, TimeUtils.getCurrentNormalizedDate())
                .onErrorResumeNext(throwable -> weatherRepository.loadExtendedWeatherFromNW(placeDetails));
    }


    @NonNull
    private Single<DailyWeather> updateExtendedWeather(PlaceDetails placeDetails) {
        return weatherRepository.loadExtendedWeatherFromNW(placeDetails);
    }

    @NonNull
    private Single<Long> clearOldRecords() {
        Calendar date = GregorianCalendar.getInstance();
        return weatherRepository.clearOldRecords(date);
    }

    @NonNull
    private PlaceDetails detailsFromModel(PlaceModel place) {
        return PlaceDetails.newInstance(place.getLocalId(),
                new Coord(place.getLat(), place.getLon()), place.getName(), place.getPlaceId());
    }
}
