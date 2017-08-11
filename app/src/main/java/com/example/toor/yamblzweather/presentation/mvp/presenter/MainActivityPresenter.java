package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by igor on 8/11/17.
 */

public class MainActivityPresenter {

    private PlacesInteractor interactor;

    @Inject
    MainActivityPresenter(PlacesInteractor interactor) {
        this.interactor = interactor;
    }

    public Single<List<PlaceModel>> getPlaces() {
        return interactor.getAllPlaces().toList().observeOn(AndroidSchedulers.mainThread());
    }
}
