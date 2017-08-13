package com.example.toor.yamblzweather.presentation;

import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.NavigateView;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


/**
 * Created by igor on 7/30/17.
 */

@RunWith(PowerMockRunner.class)
public class WeatherFragmentPresenterTest {

    @Mock WeatherInteractor weatherInteractor;
    @Mock SettingsInteractor settingsInteractor;
    @Mock PlacesInteractor placesInteractor;

    private WeatherPresenter preparePresenter(WeatherView view) {
        WeatherPresenter presenter = new WeatherPresenter(placesInteractor, weatherInteractor, settingsInteractor);
        presenter.onChildAttach(view);
        return presenter;
    }

    @Before
    public void prepare() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());

        when(weatherInteractor.getWeatherFromDB(any())).thenReturn(
                Single.fromCallable(
                        DailyWeather::new)
        );
        when(weatherInteractor.updateWeather(any())).thenReturn(
                Single.fromCallable(
                        DailyWeather::new)
        );
        when(settingsInteractor.getUserSettings()).thenReturn(
                Single.fromCallable(
                        () -> new SettingsModel.Builder(TemperatureMetric.CELSIUS, 10).build()
                )
        );
        when(placesInteractor.getAllPlaces()).thenReturn(
                Flowable.just(new PlaceModel.Builder().placeId("").name("").localId(0L).build())
        );

    }

    @Test
    public void getWeatherTest() {
        TestView testView = new TestView();
        WeatherPresenter presenter = preparePresenter(testView);
        presenter.getWeather(new PlaceModel.Builder().build(), testView);
        assertThat(testView.isWeatherDisplayed(), equalTo(true));
    }

    @Test
    public void updateWeatherTest() {
        TestView testView = new TestView();
        WeatherPresenter presenter = preparePresenter(testView);
        presenter.updateWeather(new PlaceModel.Builder().build(), testView);
        verify(weatherInteractor, times(1)).updateWeather(any());
    }

    @Test
    public void updateAllWeatherTest() {
        TestView testView = new TestView();
        WeatherPresenter presenter = preparePresenter(testView);
        presenter.updateAllWeather();
        verify(weatherInteractor, times(1)).updateWeather(any());
    }

    private static class TestView implements WeatherView {
        private boolean displayedWeather = false;
        private boolean displayedError = false;

        @Override
        public void showWeather(DailyWeather weather, String placeName) {
            displayedWeather = true;
        }

        @Override
        public void showWeather(DailyWeather weather) {

        }

        @Override
        public long getPlaceId() {
            return 0;
        }

        public boolean isWeatherDisplayed() {
            return displayedWeather;
        }

        public boolean isErrorDisplayed() {
            return displayedError;
        }
    }


}
