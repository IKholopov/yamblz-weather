package com.example.toor.yamblzweather.data.network.api;

import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by igor on 7/24/17.
 */

public interface GooglePlacesAPI {
    @GET("autocomplete/json?")
    Single<PlacesAutocompleteModel> getPlacesAutocomplete(@Query("input")String input,
                                                          @Query("types")String types,
                                                          @Query("language")String language,
                                                          @Query("key")String key);
}
