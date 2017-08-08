package com.example.toor.yamblzweather.data.repositories.weather;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.presentation.di.App;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private DataBase dataBase;
    private OWService service;

    @Inject
    Context context;
    @Inject
    Locale locale;

    public WeatherRepositoryImpl(DataBase dataBase, OWService service) {
        this.dataBase = dataBase;
        this.service = service;

        App.getInstance().getAppComponent().inject(this);
        service.setLanguage(locale);
    }

    @NonNull
    @Override
    public Single<ExtendedWeather> getExtendedWeatherFromDB(PlaceDetails placeDetails) {
        ExtendedWeather weather = new ExtendedWeather();
        City city = new City();
        city.setId(placeDetails.getId().intValue());
        city.setCoord(placeDetails.getCoords());
        city.setName(placeDetails.getName());
        weather.setCity(city);
        return dataBase.getWeather(placeDetails)
                    .toList().map(list -> {
                weather.setList(list);
                return weather;
            });
    }

    @NonNull
    @Override
    public Single<ExtendedWeather> loadExtendedWeatherFromNW(PlaceDetails placeDetails) {
        return service.getExtendedWeather(placeDetails.getCoords());
    }

    @NonNull
    @Override
    public Single<Long> clearOldRecords(Calendar date) {
        return dataBase.clearBeforeDate(date);
    }

    @NonNull
    @Override
    public Single<Long> deleteRecordsForPlace(long placeId) {
        return dataBase.deletePlace(placeId);
    }

    /*
    @Override
    public Single<ExtendedWeather> getExtendedWeatherFromDB(int cityId) {
        return dataBase.getExtendedWeather(cityId);
    }

    @Nullable
    @Override
    public Single<ExtendedWeather> getExtendedWeatherFromDB(Coord coords) {
        return dataBase.getExtendedWeather(coords);
    }

    @Nullable
    @Override
    public Single<ExtendedWeather> loadExtendedWeatherFromNW(int cityId) {
        return service.getExtendedWeather(cityId);
    }

    @Nullable
    @Override
    public Single<ExtendedWeather> loadExtendedWeatherFromNW(Coord coords) {
        return service.getExtendedWeather(coords);
    }*/

    @Override
    public void saveWeather(@NonNull ExtendedWeather weather, @NonNull PlaceDetails placeDetails) {
        dataBase.addOrUpdateWeather(weather, placeDetails.getId(), () -> {});
    }
}