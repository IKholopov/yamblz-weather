package com.example.toor.yamblzweather.data.database;

import nl.qbusict.cupboard.annotation.CompositeIndex;
import nl.qbusict.cupboard.annotation.Index;

/**
 * Model for cupboard database
 * Created by igor on 8/2/17.
 */


public class WeatherDBModel {

    public Long _id;

    public Long placeId;

    public Long date;

    public Float tempMin;
    public Float tempMax;
    public Long humidity;
    public Float pressure;
    public Float windSpeed;
    public Float windDirection;
    public String weatherDescription;
    public String weatherIcon;

    public WeatherDBModel() {
        date = 0L;
        tempMin = 0f;
        tempMax = 0f;
        humidity = 0L;
        pressure = 0f;
        windSpeed = 0f;
        windDirection = 0f;
        weatherDescription = "NO_DESCRIPTION";
        weatherIcon = "NO_ICON";
    }

    public WeatherDBModel(Long date, Long placeId, Float tempMin, Float tempMax,
                          Long humidity, Float pressure, Float windSpeed, Float windDirection,
                          String weatherDescription, String weatherIcon) {
        this.date = date;
        this.placeId = placeId;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
    }
}
