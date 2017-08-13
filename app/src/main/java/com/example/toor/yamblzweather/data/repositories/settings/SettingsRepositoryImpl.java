package com.example.toor.yamblzweather.data.repositories.settings;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.models.settings.SettingsPreference;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.network.PlacesService;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import io.reactivex.Single;

public class SettingsRepositoryImpl implements SettingsRepository {

    private SettingsPreference preference;
    private PlacesService service;

    public SettingsRepositoryImpl(SettingsPreference preference, PlacesService service) {
        this.preference = preference;
        this.service = service;
    }

    @Override
    public Single<SettingsModel> getUserSettings() {
        return preference.loadUserSettings();
    }

    @Override
    public void saveTemperatureMetric(TemperatureMetric metric) {
        preference.saveTemperatureMetric(metric);
    }

    @Override
    public void saveUpdateInterval(long interval) {
        preference.saveUpdateWeatherInterval(interval);
    }

    @Override
    public void saveSelectedCity(int cityId) {
        preference.saveSelectedCityId(cityId);
    }

    @Override
    public void saveSelectedCity(PlaceModel model) {
        preference.saveSelectedCityCoords(model.getLat(), model.getLon());
        preference.saveSelectedCityName(model.getName());
    }

    @Override
    public String getSelectedCityName() {
        return preference.loadSelectedCityName();
    }
}
