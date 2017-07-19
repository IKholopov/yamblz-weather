package com.example.toor.yamblzweather.data.network.api;

import com.example.toor.yamblzweather.data.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.weather.five_day.ExtendedWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherAPI {
    @GET("forecast?")
    Observable<ExtendedWeather> getFiveDayExtendedWeather(@Query("lat") double lat,
                                                          @Query("lon") double lon,
                                                          @Query("appid") String appId,
                                                          @Query("units") String units,
                                                          @Query("lang") String lang);

    @GET("weather?")
    Observable<CurrentWeather> getCurrentWeather(@Query("lat") double lat,
                                                 @Query("lon") double lon,
                                                 @Query("appid") String appId,
                                                 @Query("units") String units,
                                                 @Query("lang") String lang);
}
