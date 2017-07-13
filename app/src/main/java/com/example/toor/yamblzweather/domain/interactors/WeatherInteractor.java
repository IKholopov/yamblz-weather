package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.WeatherRepository.WeatherRepository;
import com.example.toor.yamblzweather.domain.providers.WeatherProvider;

public class WeatherInteractor {

    private WeatherProvider provider;

    public WeatherInteractor(WeatherProvider provider) {
        this.provider = provider;
    }

    public WeatherRepository loadWeather() {
        return provider.getCurrentWeather();
    }
}
