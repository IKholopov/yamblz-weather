package com.example.toor.yamblzweather.data.WeatherRepository;

public class WeatherRepository {

    private String city;
    private long date;
    private String weatherState;
    private int temperature;
    private int windSpeed;
    private double humidity;
    private double pressure;
    private double visibility;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public static class WeatherBuilder {

        //Required params
        private final String city;
        private final long date;
        private final String weatherState;
        private final int temperature;

        //Optional params
        private int windSpeed = 0;
        private double humidity = 0;
        private double pressure = 0;
        private double visibility = 0;

        public WeatherBuilder(String city, long date, String weatherState, int temperature) {
            this.city = city;
            this.date = date;
            this.weatherState = weatherState;
            this.temperature = temperature;
        }

        public WeatherBuilder windSpeed(int windSpeedValue) {
            windSpeed = windSpeedValue;
            return this;
        }

        public WeatherBuilder humidity(double humidityValue) {
            humidity = humidityValue;
            return this;
        }

        public WeatherBuilder pressure(double pressureValue) {
            pressure = pressureValue;
            return this;
        }

        public WeatherBuilder visibility(double visibilityValue) {
            visibility = visibilityValue;
            return this;
        }

        public WeatherRepository build() {
            return new WeatherRepository(this);
        }
    }

    private WeatherRepository(WeatherBuilder builder) {
        city = builder.city;
        date = builder.date;
        weatherState = builder.weatherState;
        temperature = builder.temperature;
        windSpeed = builder.windSpeed;
        humidity = builder.humidity;
        pressure = builder.pressure;
        visibility = builder.visibility;
    }
}
