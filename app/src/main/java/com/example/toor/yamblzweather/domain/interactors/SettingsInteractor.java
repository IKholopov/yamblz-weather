package com.example.toor.yamblzweather.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlaceName;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import java.util.ArrayList;

import io.reactivex.Single;

public class SettingsInteractor {

    private SettingsRepository repository;

    public SettingsInteractor(SettingsRepository repository) {
        this.repository = repository;
    }

    public @NonNull Single<SettingsModel> getUserSettings() {
        return repository.getUserSettings();
    }

    public void saveTemperatureMetric(TemperatureMetric metric) {
        repository.saveTemperatureMetric(metric);
    }

    public void saveUpdateInterval(long interval) {
        repository.saveUpdateInterval(interval);
    }
}
