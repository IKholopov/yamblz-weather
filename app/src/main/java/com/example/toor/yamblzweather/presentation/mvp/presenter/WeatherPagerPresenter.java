package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherPagerView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by igor on 8/6/17.
 */

public class WeatherPagerPresenter extends BaseFragmentPresenter<WeatherPagerView> {
    private static final String TAG = "WeatherPagerPresenter";

    private WeatherInteractor weatherInteractor;
    private PlacesInteractor placesInteractor;

    @Inject
    public WeatherPagerPresenter(WeatherInteractor weatherInteractor,
                                 PlacesInteractor placesInteractor) {
        this.weatherInteractor = weatherInteractor;
        this.placesInteractor = placesInteractor;
    }

    public Single<List<PlaceModel>> getPlaces() {
        return placesInteractor.getAllPlaces().toList();
    }
}
