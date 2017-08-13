package com.example.toor.yamblzweather.data.repositories.settings;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;

import io.reactivex.Single;

public interface SettingsRepository {

    @NonNull
    Single<SettingsModel> getUserSettings();

    void saveTemperatureMetric(TemperatureMetric metric);

    void saveUpdateInterval(long interval);

    void saveSelectedCity(int cityId);
    void saveSelectedCity(PlaceModel model);

    String getSelectedCityName();
}
