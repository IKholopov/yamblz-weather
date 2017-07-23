package com.example.toor.yamblzweather.data.network;

import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.network.api.OpenWeatherAPI;
import com.example.toor.yamblzweather.domain.utils.OWSupportedLanguages;

import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OWService {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private final OpenWeatherAPI mOpenWeatherAPI;
    private String mToken;
    private OWSupportedLanguages mSelectedLanguage = OWSupportedLanguages.ENGLISH;

    /**
     * Main Service constructor.
     *
     * @param token the Open Weather Token to be used for the API calls.
     */
    public OWService(final String token) {
        mToken = token;

        Retrofit mRetrofitOWInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mOpenWeatherAPI = mRetrofitOWInstance.create(OpenWeatherAPI.class);
    }

    /**
     * Sets the API language based on a Locale.
     *
     * @param language Locale of current language.
     */
    public void setLanguage(Locale language) {
        switch (language.getLanguage()) {
            case "en":
                mSelectedLanguage = OWSupportedLanguages.ENGLISH;
                break;
            case "ru":
                mSelectedLanguage = OWSupportedLanguages.RUSSIAN;
                break;
            case "it":
                mSelectedLanguage = OWSupportedLanguages.ITALIAN;
                break;
            case "es":
                mSelectedLanguage = OWSupportedLanguages.SPANISH;
                break;
            case "ro":
                mSelectedLanguage = OWSupportedLanguages.ROMANIAN;
                break;
            case "pl":
                mSelectedLanguage = OWSupportedLanguages.POLISH;
                break;
            case "fi":
                mSelectedLanguage = OWSupportedLanguages.FINNISH;
                break;
            case "nl":
                mSelectedLanguage = OWSupportedLanguages.DUTCH;
                break;
            case "fr":
                mSelectedLanguage = OWSupportedLanguages.FRENCH;
                break;
            case "bg":
                mSelectedLanguage = OWSupportedLanguages.BULGARIAN;
                break;
            case "sv":
                mSelectedLanguage = OWSupportedLanguages.SWEDISH;
                break;
            case "zh_tw":
                mSelectedLanguage = OWSupportedLanguages.CHINESE_T;
                break;
            case "zh":
                mSelectedLanguage = OWSupportedLanguages.CHINESE_S;
                break;
            case "tr":
                mSelectedLanguage = OWSupportedLanguages.TURKISH;
                break;
            case "hr":
                mSelectedLanguage = OWSupportedLanguages.CROATIAN;
                break;
            case "co":
                mSelectedLanguage = OWSupportedLanguages.CATALAN;
                break;
            default:
                mSelectedLanguage = OWSupportedLanguages.ENGLISH;
                break;
        }
    }

    public
    @Nullable
    Single<CurrentWeather> getCurrentWeather(int cityId) {
        return mOpenWeatherAPI.getCurrentWeather(
                cityId,
                mToken,
                mSelectedLanguage.getLanguageLocale())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public
    @Nullable
    Single<CurrentWeather> getCurrentWeatherForCoords(Coord coords) {
        return mOpenWeatherAPI.getCurrentWeatherForCoords(
                coords.getLat(),
                coords.getLon(),
                mToken,
                mSelectedLanguage.getLanguageLocale())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public
    @Nullable
    Single<ExtendedWeather> getExtendedWeather(int cityId) {
        return mOpenWeatherAPI.getFiveDayExtendedWeather(
                cityId,
                mToken,
                mSelectedLanguage.getLanguageLocale())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public
    @Nullable
    Single<ExtendedWeather> getExtendedWeather(Coord coords) {
        return mOpenWeatherAPI.getFiveDayExtendedWeather(
                coords.getLat(),
                coords.getLon(),
                mToken,
                mSelectedLanguage.getLanguageLocale())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
