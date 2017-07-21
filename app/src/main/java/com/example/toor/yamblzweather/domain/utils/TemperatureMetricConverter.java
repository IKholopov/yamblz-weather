package com.example.toor.yamblzweather.domain.utils;

public class TemperatureMetricConverter {

    public static int getSupportedTemperature(double temperatureInKelvin, TemperatureMetric metric) {
        switch (metric) {
            case CELSIUS:
                return (int) Math.round(temperatureInKelvin - 273.15);
            case FAHRENHEIT:
                return (int) Math.round(1.8 * (temperatureInKelvin - 273.15) + 32);
            default:
                return (int) Math.round(temperatureInKelvin);
        }
    }
}
