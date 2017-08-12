package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.PlacesListView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

/**
 * Created by igor on 8/7/17.
 */

public class PlacesListPresenter extends BaseFragmentPresenter<PlacesListView> {

    private static final String TAG = PlacesListPresenter.class.getSimpleName();

    private static final int AUTOCOMPLETE_CALLDOWN = 800;
    private static final int NETWORK_TIMEOUT = 5000;


    private PlacesInteractor interactor;

    @Inject
    public PlacesListPresenter (PlacesInteractor interactor) {
        this.interactor = interactor;
    }

    public Flowable<PlaceModel> getPlaces() {
        return interactor.getAllPlaces().observeOn(AndroidSchedulers.mainThread());
    }

    public void subscribeOnPlaceNameChanges(Observable<CharSequence> placeNameSource) {
        unSubcribeOnDetach(placeNameSource.throttleLast(AUTOCOMPLETE_CALLDOWN, TimeUnit.MILLISECONDS).subscribe(
                input -> unSubcribeOnDetach(interactor.getAutocomplete(input.toString()).subscribe(
                        places -> {
                            PlacesListView view = getView();
                            if (view != null) {
                                view.updatePlacesSuggestionList(places);
                            }
                        },
                        error -> {
                            Log.e(TAG, "Failed to fetch place suggestions");
                        }
                )
        )));
    }

    public Single<PlaceModel> fetchAndSaveCityDetails(PlaceModel placeModel, Action onInserted) {
        Single<PlaceModel> request = interactor.getPlaceDetails(placeModel.getPlaceId());
        unSubcribeOnDetach(request.subscribe(
                place -> {
                    interactor.addPlace(place, onInserted);
                },
                error -> {
                    Log.e(TAG, "Failed to fetch city details: " + error.getLocalizedMessage());
                }
        ));
        return request.timeout(NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> deletePlace(PlaceModel placeModel) {
        Long id = placeModel.getLocalId();
        if(id != null) {
            return interactor.deletePlace(id);
        }
        return Single.just(0L);
    }
}
