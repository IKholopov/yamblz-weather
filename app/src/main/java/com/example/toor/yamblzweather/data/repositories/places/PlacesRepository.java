package com.example.toor.yamblzweather.data.repositories.places;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;

/**
 * Created by igor on 8/6/17.
 */

public interface PlacesRepository {

    @NonNull
    Flowable<PlaceDetails> getAllPlaces();

    @NonNull
    Single<Long> deletePlace(long placeId);

    @Nullable
    Single<PlacesAutocompleteModel> loadPlacesAutocomplete(String input);

    @Nullable
    Single<PlaceDetails> loadPlaceDetails(String placeId);

    void addPlace(PlaceDetails place, @NonNull Action onSuccess);
}
