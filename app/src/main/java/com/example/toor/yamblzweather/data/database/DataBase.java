package com.example.toor.yamblzweather.data.database;

import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;

import java.util.Calendar;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;

public interface DataBase {

    @NonNull
    Flowable<PlaceDetails> getPlaces();

    @NonNull
    Flowable<DailyForecastElement> getWeather(@NonNull PlaceDetails placeDetails, long dateSec);

    @NonNull
    Single<Long> clearBeforeDate(@NonNull Calendar date);

    @NonNull
    Single<Long> deletePlace(Long placeId);

    @NonNull
    Single<Long> deleteWeatherForPlace(Long placeId);

    void addOrUpdateWeather(@NonNull DailyWeather weatherModel, Long placeId,
                            @NonNull Action onComplete);

    void addPlace(@NonNull PlaceDetails place, @NonNull Action onComplete);

    void addCurrentLocation(@NonNull PlaceDetails place, @NonNull Action onComplete);
}
