package com.example.toor.yamblzweather.presentation;

import com.example.toor.yamblzweather.common.AppMock;
import com.example.toor.yamblzweather.common.SyncEntity;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.scheduler.WeatherScheduleJob;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.components.AppComponent;
import com.example.toor.yamblzweather.presentation.di.modules.UtilsModule;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.SettingsFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.SettingsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.PublishSubject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by igor on 7/30/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SettingsFragmentPresenter.class, App.class, UtilsModule.class})
public class SettingsFragmentPresenterTest {

    private static final String TEST_ID = "some_id";
    private static final String TEST_SEQUENCE = "some_test_sequence";

    private AppMock mockedApp;

    @Mock SettingsInteractor interactor;
    @Mock WeatherScheduleJob job;

    private SettingsFragmentPresenter preparePresenter(TestView view) {
        SettingsFragmentPresenter presenter = new SettingsFragmentPresenter(interactor);
        presenter.onAttach(view);
        return presenter;
    }

    @Before
    public void prepare() throws Exception {
        mockedApp = AppMock.newInstance();
        PowerMockito.mockStatic(WeatherScheduleJob.class);
        whenNew(WeatherScheduleJob.class).withNoArguments().thenReturn(job);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());

        when(interactor.getUserSettings()).thenReturn(Single.fromCallable(()
                -> new SettingsModel.Builder(TemperatureMetric.CELSIUS, 10).build()));
        /*when(interactor.getPlaceDetails(TEST_ID)).thenReturn(Single.fromCallable(()
                -> new PlaceModel.Builder().placeId(TEST_ID).build()));
        when(interactor.getAutocomplete(TEST_SEQUENCE)).thenReturn(Single.fromCallable(()
                -> {
                    ArrayList<PlaceModel> list = new ArrayList<PlaceModel>();
                    list.add(new PlaceModel.Builder().name(TEST_SEQUENCE).build());
                    return list;
                }
            ));*/
    }

    @Test
    public void showSettingsTest() {
        TestView view = new TestView();
        SettingsFragmentPresenter presenter = preparePresenter(view);
        presenter.showSettings();
        assertThat(view.isSettingsSet(), equalTo(true));
    }

    /*@Test
    public void fetchAndSaveCityDetailsTest() {
        TestView view = new TestView();
        SettingsFragmentPresenter presenter = preparePresenter(view);
        TestObserver<PlaceModel> observer = new TestObserver<>();
        presenter.fetchAndSaveCityDetails(new PlaceModel.Builder().placeId(TEST_ID).build())
                .subscribe(observer);
        observer.awaitTerminalEvent();
        observer.assertValue(model -> model.getPlaceId().equals(TEST_ID));
        verify(interactor, times(1)).saveSelectedCity(any());
    }

    @Test
    public void subscribeOnCityNameChangesTest() throws InterruptedException {
        TestView view = new TestView();
        SyncEntity syncEntity = new SyncEntity();
        SettingsFragmentPresenter presenter = preparePresenter(view);
        TestScheduler scheduler = new TestScheduler();
        PublishSubject<CharSequence> publisher = PublishSubject.create();
        presenter.subscribeOnCityNameChanges(publisher.observeOn(scheduler));
        publisher.onNext(TEST_SEQUENCE);
        scheduler.advanceTimeBy(2, TimeUnit.MILLISECONDS);
        publisher.onComplete();
        syncEntity.waitTimeout(2000);
        assertThat(view.getPlaces().size(), equalTo(1));
        assertThat(view.getPlaces().get(0).getName(), equalTo(TEST_SEQUENCE));
    }*/


    private static class TestView implements SettingsView {

        private boolean settingsSet = false;
        private ArrayList<PlaceModel> places;

        @Override
        public void setSettings(SettingsModel settingsModel) {
            settingsSet = true;
        }

        @Override
        public void updateCitiesSuggestionList(ArrayList<PlaceModel> places) {
            this.places = places;
        }

        @Override
        public void displayError(String message) {
        }

        public boolean isSettingsSet() {
            return settingsSet;
        }
        public ArrayList<PlaceModel> getPlaces() {
            return places;
        }
    }
}
