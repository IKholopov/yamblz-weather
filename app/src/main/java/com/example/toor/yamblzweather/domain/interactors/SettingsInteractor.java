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

    public void saveSelectedCity(int cityId) {
        repository.saveSelectedCity(cityId);
    }

    public void saveSelectedCity(PlaceModel city) {
        repository.saveSelectedCity(city);
    }

    public String getSelectedCityName() {
        return repository.getSelectedCityName();
    }

    public Single<ArrayList<PlaceModel>> getAutocomplete(String input) {
        return repository.loadPlacesAutocomplete(input).map(this::modelFromAutocomplete);
    }

    public Single<PlaceModel> getPlaceDetails(String placeId) {
        return repository.loadPlaceDetails(placeId).map(this::modelFromDetails);
    }

    private
    @Nullable
    ArrayList<PlaceModel> modelFromAutocomplete(PlacesAutocompleteModel model) {
        if(model == null) {
            return null;
        }
        ArrayList<PlaceModel> transformed = new ArrayList<>();
        for(int i = 0; i < model.getSize(); ++i) {
            PlaceName place = model.getPredictionAt(i);
            transformed.add(new PlaceModel.Builder().name(place.getName())
                    .placeId(place.getPlaceId()).build());
        }
        return transformed;
    }

    private
    @Nullable
    PlaceModel modelFromDetails(PlaceDetails details) {
        if(details == null) {
            return null;
        }
        return new PlaceModel.Builder().name(details.getName()).coords(details.getCoords().getLat(),
                details.getCoords().getLon()).build();
    }
}
