package com.example.toor.yamblzweather.data.database;

import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.WeatherForecastElement;

import java.util.Calendar;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;

public interface DataBase {

    @NonNull
    Flowable<PlaceDetails> getPlaces();

    @NonNull
    Flowable<WeatherForecastElement> getWeather(@NonNull PlaceDetails placeDetails);

    @NonNull
    Single<Long> clearBeforeDate(@NonNull Calendar date);

    @NonNull
    Single<Long> deletePlace(long placeId);

    @NonNull
    Single<Long> deleteWeatherForPlace(long placeId);

    void addOrUpdateWeather(@NonNull ExtendedWeather weatherModel, long placeId,
                            @NonNull Action onComplete);

    void addPlace(@NonNull PlaceDetails place, @NonNull Action onComplete);

    void addCurrentLocation(@NonNull PlaceDetails place, @NonNull Action onComplete);
}
