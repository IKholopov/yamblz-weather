package com.example.toor.yamblzweather.data.repositories.weather;

import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;

import java.util.Calendar;

import io.reactivex.Single;

public interface WeatherRepository {

    /*@Nullable
    Single<CurrentWeather> getCurrentWeatherFromDB(int cityId);

    @Nullable
    Single<CurrentWeather> getCurrentWeatherFromDB(Coord coord);

    @Nullable
    Single<CurrentWeather> loadCurrentWeatherFromNW(int cityId);

    @Nullable
    Single<CurrentWeather> loadCurrentWeatherFromNW(Coord coord);*/

    @NonNull
    Single<DailyWeather> getExtendedWeatherFromDB(PlaceDetails placeDetails, long dateSec);

    /*@Nullable
    Single<ExtendedWeather> getExtendedWeatherFromDB(Coord coords);*/

    @NonNull
    Single<DailyWeather> loadExtendedWeatherFromNW(PlaceDetails placeDetails);

    @NonNull
    Single<Long> clearOldRecords(Calendar date);

    @NonNull
    Single<Long> deleteRecordsForPlace(long placeId);

    /*@Nullable
    Single<ExtendedWeather> loadExtendedWeatherFromNW(Coord coords);*/

    void saveWeather(@NonNull DailyWeather weather, @NonNull PlaceDetails placeDetails);
}