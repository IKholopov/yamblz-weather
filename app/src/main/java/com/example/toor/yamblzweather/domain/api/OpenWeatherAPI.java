package com.example.toor.myopenweather;

import com.example.toor.myopenweather.model.gson.current_day.CurrentWeather;
import com.example.toor.myopenweather.model.gson.five_day.ExtendedWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherAPI {
    @GET("forecast?")
    Call<ExtendedWeather> getFiveDayExtendedWeather(@Query("lat") double lat,
                                                    @Query("lon") double lon,
                                                    @Query("appid") String appId,
                                                    @Query("units") String units,
                                                    @Query("lang") String lang);

    @GET("weather?")
    Call<CurrentWeather> getCurrentWeather(@Query("lat") double lat,
                                           @Query("lon") double lon,
                                           @Query("appid") String appId,
                                           @Query("units") String units,
                                           @Query("lang") String lang);
}
