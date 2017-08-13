package com.example.toor.yamblzweather.data.network;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.network.api.GooglePlacesAPI;
import com.example.toor.yamblzweather.domain.utils.APISupportedLanguages;
import com.google.gson.GsonBuilder;

import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by igor on 7/24/17.
 */

public class PlacesService {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String PLACE_TYPES = "(cities)";

    private final GooglePlacesAPI api;
    private String apiKey;
    private APISupportedLanguages selectedLanguage = APISupportedLanguages.ENGLISH;

    public PlacesService(String apiKey) {
        this.apiKey = apiKey;

        Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofitInstance.create(GooglePlacesAPI.class);
    }

    /**
     * Sets the API language based on a Locale.
     *
     * @param language Locale of current language.
     */
    public void setLanguage(Locale language) {
        switch (language.getLanguage()) {
            case "en":
                selectedLanguage = APISupportedLanguages.ENGLISH;
                break;
            case "ru":
                selectedLanguage = APISupportedLanguages.RUSSIAN;
                break;
            case "it":
                selectedLanguage = APISupportedLanguages.ITALIAN;
                break;
            case "es":
                selectedLanguage = APISupportedLanguages.SPANISH;
                break;
            case "ro":
                selectedLanguage = APISupportedLanguages.ROMANIAN;
                break;
            case "pl":
                selectedLanguage = APISupportedLanguages.POLISH;
                break;
            case "fi":
                selectedLanguage = APISupportedLanguages.FINNISH;
                break;
            case "nl":
                selectedLanguage = APISupportedLanguages.DUTCH;
                break;
            case "fr":
                selectedLanguage = APISupportedLanguages.FRENCH;
                break;
            case "bg":
                selectedLanguage = APISupportedLanguages.BULGARIAN;
                break;
            case "sv":
                selectedLanguage = APISupportedLanguages.SWEDISH;
                break;
            case "zh_tw":
                selectedLanguage = APISupportedLanguages.CHINESE_T;
                break;
            case "zh":
                selectedLanguage = APISupportedLanguages.CHINESE_S;
                break;
            case "tr":
                selectedLanguage = APISupportedLanguages.TURKISH;
                break;
            case "hr":
                selectedLanguage = APISupportedLanguages.CROATIAN;
                break;
            case "co":
                selectedLanguage = APISupportedLanguages.CATALAN;
                break;
            default:
                selectedLanguage = APISupportedLanguages.ENGLISH;
                break;
        }
    }

    public
    @NonNull
    Single<PlacesAutocompleteModel> getAutocompleteForInput(String input) {
        return api.getPlacesAutocomplete(input, PLACE_TYPES,
                selectedLanguage.getLanguageLocale(), apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public
    @NonNull
    Single<PlaceDetails> getPlaceDetails(String placeId) {
        return api.getPlaceDetails(placeId, selectedLanguage.getLanguageLocale(), apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
