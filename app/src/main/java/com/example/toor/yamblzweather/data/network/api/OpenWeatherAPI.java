package com.example.toor.yamblzweather.data.network.api;

import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherAPI {
    @GET("forecast?")
    Single<ExtendedWeather> getFiveDayExtendedWeather(@Query("id") int cityId,
                                                      @Query("appid") String appId,
                                                      @Query("lang") String lang);

    @GET("forecast?")
    Single<ExtendedWeather> getFiveDayExtendedWeather(@Query("lat") double lat,
                                                      @Query("lon") double lon,
                                                      @Query("appid") String appId,
                                                      @Query("lang") String lang);

    @GET("weather?")
    Single<CurrentWeather> getCurrentWeather(@Query("id") int cityId,
                                             @Query("appid") String appId,
                                             @Query("lang") String lang);

    @GET("weather?")
    Single<CurrentWeather> getCurrentWeatherForCoords(@Query("lat") double lat,
                                             @Query("lon") double lon,
                                             @Query("appid") String appId,
                                             @Query("lang") String lang);
}
