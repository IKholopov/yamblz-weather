package com.example.toor.yamblzweather.data.network;


import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.network.api.GooglePlacesAPI;
import com.example.toor.yamblzweather.domain.utils.GooglePlacesSupportedLanguages;
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
    private GooglePlacesSupportedLanguages selectedLanguage = GooglePlacesSupportedLanguages.ENGLISH;

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
                selectedLanguage = GooglePlacesSupportedLanguages.ENGLISH;
                break;
            case "ru":
                selectedLanguage = GooglePlacesSupportedLanguages.RUSSIAN;
                break;
            case "it":
                selectedLanguage = GooglePlacesSupportedLanguages.ITALIAN;
                break;
            case "es":
                selectedLanguage = GooglePlacesSupportedLanguages.SPANISH;
                break;
            case "ro":
                selectedLanguage = GooglePlacesSupportedLanguages.ROMANIAN;
                break;
            case "pl":
                selectedLanguage = GooglePlacesSupportedLanguages.POLISH;
                break;
            case "fi":
                selectedLanguage = GooglePlacesSupportedLanguages.FINNISH;
                break;
            case "nl":
                selectedLanguage = GooglePlacesSupportedLanguages.DUTCH;
                break;
            case "fr":
                selectedLanguage = GooglePlacesSupportedLanguages.FRENCH;
                break;
            case "bg":
                selectedLanguage = GooglePlacesSupportedLanguages.BULGARIAN;
                break;
            case "sv":
                selectedLanguage = GooglePlacesSupportedLanguages.SWEDISH;
                break;
            case "zh_tw":
                selectedLanguage = GooglePlacesSupportedLanguages.CHINESE_T;
                break;
            case "zh":
                selectedLanguage = GooglePlacesSupportedLanguages.CHINESE_S;
                break;
            case "tr":
                selectedLanguage = GooglePlacesSupportedLanguages.TURKISH;
                break;
            case "hr":
                selectedLanguage = GooglePlacesSupportedLanguages.CROATIAN;
                break;
            case "co":
                selectedLanguage = GooglePlacesSupportedLanguages.CATALAN;
                break;
            default:
                selectedLanguage = GooglePlacesSupportedLanguages.ENGLISH;
                break;
        }
    }

    public
    @Nullable
    Single<PlacesAutocompleteModel> getAutocompleteForInput(String input) {
        return api.getPlacesAutocomplete(input, PLACE_TYPES,
                selectedLanguage.getLanguageLocale(), apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
