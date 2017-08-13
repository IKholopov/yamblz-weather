package com.example.toor.yamblzweather.domain.interactors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlaceName;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;

/**
 * Created by igor on 8/6/17.
 */

public class PlacesInteractor {
    private WeatherRepository weatherRepository;
    private PlacesRepository placesRepository;

    public PlacesInteractor(WeatherRepository weatherRepository, PlacesRepository placesRepository) {
        this.weatherRepository = weatherRepository;
        this.placesRepository = placesRepository;
    }

    public Single<ArrayList<PlaceModel>> getAutocomplete(String input) {
        return placesRepository.loadPlacesAutocomplete(input).map(this::modelFromAutocomplete);
    }

    public Single<PlaceModel> getPlaceDetails(String placeId) {
        return placesRepository.loadPlaceDetails(placeId).map(this::modelFromDetails);
    }

    public Flowable<PlaceModel> getAllPlaces() {
        return placesRepository.getAllPlaces().map(this::modelFromDetails);
    }

    public Single<Long> deletePlace(Long localPlaceId) {
        return placesRepository.deletePlace(localPlaceId).doOnSuccess(deleted ->
            weatherRepository.deleteRecordsForPlace(localPlaceId).subscribe());
    }

    public void addPlace(PlaceModel placeModel, @NonNull Action onSuccess) {
        placesRepository.addPlace(detailsFromModel(placeModel), onSuccess);
    }

    @Nullable
    private ArrayList<PlaceModel> modelFromAutocomplete(PlacesAutocompleteModel model) {
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

    @Nullable private PlaceModel modelFromDetails(PlaceDetails details) {
        if(details == null) {
            return null;
        }
        return new PlaceModel.Builder().name(details.getName()).placeId(details.getApiId())
                .coords(details.getCoords().getLat(), details.getCoords().getLon())
                .localId(details.getId()).build();
    }

    private
    @Nullable
    PlaceDetails detailsFromModel(PlaceModel placeModel) {
        if(placeModel == null) {
            return null;
        }
        return PlaceDetails.newInstance(null, new Coord(placeModel.getLat(), placeModel.getLon()),
                placeModel.getName(), placeModel.getPlaceId());
    }
}
