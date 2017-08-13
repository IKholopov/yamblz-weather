package com.example.toor.yamblzweather.data.repositories.places;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.network.PlacesService;
import com.example.toor.yamblzweather.presentation.di.App;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;

/**
 * Created by igor on 8/6/17.
 */

public class PlacesRepositoryImpl implements PlacesRepository {

    private DataBase dataBase;
    private PlacesService service;

    @Inject
    Locale locale;

    public PlacesRepositoryImpl(DataBase dataBase, PlacesService service) {
        this.dataBase = dataBase;
        this.service = service;

        App.getInstance().getAppComponent().inject(this);
        service.setLanguage(locale);
    }

    @NonNull
    @Override
    public Flowable<PlaceDetails> getAllPlaces() {
        return dataBase.getPlaces();
    }

    @NonNull
    @Override
    public Single<Long> deletePlace(long placeId) {
        return dataBase.deletePlace(placeId);
    }

    @NonNull
    @Override
    public Single<PlacesAutocompleteModel> loadPlacesAutocomplete(String input) {
        return service.getAutocompleteForInput(input);
    }

    @NonNull
    @Override
    public Single<PlaceDetails> loadPlaceDetails(String placeId) {
        return service.getPlaceDetails(placeId);
    }

    @Override
    public void addPlace(PlaceDetails place, @NonNull Action onSuccess) {
        dataBase.addPlace(place, onSuccess);
    }
}
