package com.example.toor.yamblzweather.domain.providers;

import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

public class WeatherProvider implements YahooWeatherInfoListener {

    private int currentTemp;

    @Override
    public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
        if (weatherInfo != null) {
            this.currentTemp = weatherInfo.getCurrentTemp();
        }
    }

    public int getCurrentTemp() {
        return currentTemp;
    }
}
