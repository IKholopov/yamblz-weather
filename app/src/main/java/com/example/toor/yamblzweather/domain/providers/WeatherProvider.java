package com.example.toor.yamblzweather.domain.providers;

import android.content.Context;

import com.example.toor.yamblzweather.data.WeatherRepository.WeatherRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

public class WeatherProvider implements YahooWeatherInfoListener {

    private Context context;
    private WeatherRepository weather;

    private YahooWeather yahooWeather;

    @Inject
    public WeatherProvider(Context context, WeatherRepository weather) {
        this.context = context;
        this.weather = weather;
        yahooWeather = YahooWeather.getInstance(5000, true);
    }

    @Override
    public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
        searchByPlaceName("moscow");
        if (weatherInfo != null) {
            if (yahooWeather.getSearchMode() == YahooWeather.SEARCH_MODE.GPS) {
                if (weatherInfo.getAddress() != null) {
                    weather.setCity(YahooWeather.addressToPlaceName(weatherInfo.getAddress()));
                }
            }

            String city = weatherInfo.getLocationCity();
            long date = convertStringDateToLong(weatherInfo.getCurrentConditionDate());
            String weatherState = weatherInfo.getCurrentText();
            int temperature = weatherInfo.getCurrentTemp();

            weather = new WeatherRepository.WeatherBuilder(city, date, weatherState, temperature)
                    .windSpeed(Integer.valueOf(weatherInfo.getWindSpeed()))
                    .humidity(Double.valueOf(weatherInfo.getAtmosphereHumidity()))
                    .pressure(Double.valueOf(weatherInfo.getAtmospherePressure()))
                    .visibility(Double.valueOf(weatherInfo.getAtmosphereVisibility()))
                    .build();
        }
    }

    private void searchByPlaceName(String location) {
        yahooWeather.setNeedDownloadIcons(true);
        yahooWeather.setUnit(YahooWeather.UNIT.CELSIUS);
        yahooWeather.setSearchMode(YahooWeather.SEARCH_MODE.PLACE_NAME);
        yahooWeather.queryYahooWeatherByPlaceName(context, location, this);
    }

    private long convertStringDateToLong(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aaa");
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public WeatherRepository getCurrentWeather() {
        return weather;
    }

}
