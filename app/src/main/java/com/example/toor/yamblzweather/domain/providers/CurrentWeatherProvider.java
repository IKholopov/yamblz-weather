package com.example.toor.yamblzweather.domain.providers;

import com.example.toor.yamblzweather.domain.service.OWService;

import javax.inject.Inject;

public class CurrentWeatherProvider {

    @Inject
    OWService service;

    public CurrentWeatherProvider(OWService service) {
        this.service = service;
    }

    public OWService provideService() {
        return service;
    }
}
