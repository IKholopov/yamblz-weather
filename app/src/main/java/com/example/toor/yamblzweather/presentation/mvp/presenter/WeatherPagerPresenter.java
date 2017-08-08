package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.di.scopes.ActivityScope;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherPagerView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by igor on 8/6/17.
 */

@ActivityScope
public class WeatherPagerPresenter extends BaseFragmentPresenter<WeatherPagerView> {
    private static final String TAG = "WeatherPagerPresenter";

    private WeatherInteractor weatherInteractor;
    private PlacesInteractor placesInteractor;

    PublishSubject<List<PlaceModel>> places = PublishSubject.create();

    @Inject
    public WeatherPagerPresenter(WeatherInteractor weatherInteractor,
                                 PlacesInteractor placesInteractor) {
        this.weatherInteractor = weatherInteractor;
        this.placesInteractor = placesInteractor;

    }

    public Single<List<PlaceModel>> getPlaces() {
        return placesInteractor.getAllPlaces().toList().observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<PlaceModel>> observePlaces() {
        return places;
    }

    public void updatePlacesList() {
        placesInteractor.getAllPlaces().toList().observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> places.onNext(list));
    }
}
