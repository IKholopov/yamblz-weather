package com.example.toor.yamblzweather.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.network.api.OpenWeatherAPI;
import com.example.toor.yamblzweather.domain.utils.APISupportedLanguages;

import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OWService {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/";

    private final OpenWeatherAPI mOpenWeatherAPI;
    private String mToken;
    private APISupportedLanguages mSelectedLanguage = APISupportedLanguages.ENGLISH;

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
    public void setLanguage(@NonNull Locale language) {
        switch (language.getLanguage()) {
            case "en":
                mSelectedLanguage = APISupportedLanguages.ENGLISH;
                break;
            case "ru":
                mSelectedLanguage = APISupportedLanguages.RUSSIAN;
                break;
            case "it":
                mSelectedLanguage = APISupportedLanguages.ITALIAN;
                break;
            case "es":
                mSelectedLanguage = APISupportedLanguages.SPANISH;
                break;
            case "ro":
                mSelectedLanguage = APISupportedLanguages.ROMANIAN;
                break;
            case "pl":
                mSelectedLanguage = APISupportedLanguages.POLISH;
                break;
            case "fi":
                mSelectedLanguage = APISupportedLanguages.FINNISH;
                break;
            case "nl":
                mSelectedLanguage = APISupportedLanguages.DUTCH;
                break;
            case "fr":
                mSelectedLanguage = APISupportedLanguages.FRENCH;
                break;
            case "bg":
                mSelectedLanguage = APISupportedLanguages.BULGARIAN;
                break;
            case "sv":
                mSelectedLanguage = APISupportedLanguages.SWEDISH;
                break;
            case "zh_tw":
                mSelectedLanguage = APISupportedLanguages.CHINESE_T;
                break;
            case "zh":
                mSelectedLanguage = APISupportedLanguages.CHINESE_S;
                break;
            case "tr":
                mSelectedLanguage = APISupportedLanguages.TURKISH;
                break;
            case "hr":
                mSelectedLanguage = APISupportedLanguages.CROATIAN;
                break;
            case "co":
                mSelectedLanguage = APISupportedLanguages.CATALAN;
                break;
            default:
                mSelectedLanguage = APISupportedLanguages.ENGLISH;
                break;
        }
    }

    public
    @Nullable
    @Deprecated
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
    @NonNull
    Single<DailyWeather> getExtendedWeather(Coord coords) {
        return mOpenWeatherAPI.getFiveDayExtendedWeather(
                coords.getLat(),
                coords.getLon(),
                mToken,
                mSelectedLanguage.getLanguageLocale(), 12)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
