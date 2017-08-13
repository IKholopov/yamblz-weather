package com.example.toor.yamblzweather.data.network.api;

import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherAPI {
    @GET("daily?")
    Single<DailyWeather> getFiveDayExtendedWeather(@Query("lat") double lat,
                                                   @Query("lon") double lon,
                                                   @Query("appid") String appId,
                                                   @Query("lang") String lang,
                                                   @Query("cnt") int days);
}
