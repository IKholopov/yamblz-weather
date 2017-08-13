package com.example.toor.yamblzweather.presentation;

import com.example.toor.yamblzweather.common.SyncEntity;
import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.PlacesListPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.PlacesListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by igor on 8/13/17.
 */

@RunWith(PowerMockRunner.class)
public class PlacesListPresenterTest {

    private PlacesListPresenter presenter;

    @Mock PlacesInteractor interactor;
    @Mock PlacesListView testView;

    @Before
    public void prepare() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());

        when(interactor.getAllPlaces())
                .thenReturn(Flowable.just(new PlaceModel.Builder().name("")
                .coords(0, 0).localId(0L).placeId("").build()));
        when(interactor.getPlaceDetails(anyString()))
                .thenReturn(Single.fromCallable(() ->
                    new PlaceModel.Builder().name("")
                            .coords(0, 0).localId(0L).placeId("").build()));
        presenter = new PlacesListPresenter(interactor);
        presenter.onAttach(testView);
    }

    @Test
    public void testGetPlaces() {
        TestSubscriber<PlaceModel> testObserver = new TestSubscriber<>();
        presenter.getPlaces().subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValue(placeModel -> placeModel.getLocalId() == 0);
    }

    @Test
    public void testFetchAndSaveCityDetails() throws InterruptedException {
        TestObserver<PlaceModel> testObserver = new TestObserver<>();
        presenter.fetchAndSaveCityDetails(new PlaceModel.Builder().name("")
                .coords(0, 0).localId(1L).placeId("").build(), () -> {
        }).subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValue(placeModel -> placeModel.getLocalId() == 0L);
    }
}
