package com.example.toor.yamblzweather.presentation;

import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.domain.interactors.SettingsInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.WeatherFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.WeatherView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Single;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.only;
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

    private WeatherFragmentPresenter preparePresenter(WeatherView view) {
        WeatherFragmentPresenter presenter = new WeatherFragmentPresenter(weatherInteractor, settingsInteractor);
        presenter.onAttach(view);
        return presenter;
    }

    @Before
    public void prepare() {
        when(weatherInteractor.getFullWeatherFromDB(any())).thenReturn(
                Single.fromCallable(
                        () -> new FullWeatherModel(new CurrentWeather(), new ExtendedWeather(), TemperatureMetric.CELSIUS )
                )
        );
        when(weatherInteractor.updateWeather(any())).thenReturn(
                Single.fromCallable(
                        () -> new FullWeatherModel(new CurrentWeather(), new ExtendedWeather(), TemperatureMetric.CELSIUS )
                )
        );
        when(settingsInteractor.getUserSettings()).thenReturn(
                Single.fromCallable(
                        () -> new SettingsModel.Builder(TemperatureMetric.CELSIUS, 10).build()
                )
        );
    }

    @Test
    public void getWeatherTest() {
        TestView testView = new TestView();
        WeatherFragmentPresenter presenter = preparePresenter(testView);
        presenter.getWeather();
        assertThat(testView.isWeatherDisplayed(), equalTo(true));
    }

    @Test
    public void updateWeatherTest() {
        TestView testView = new TestView();
        WeatherFragmentPresenter presenter = preparePresenter(testView);
        presenter.updateWeather();
        verify(weatherInteractor, only()).updateWeather(any());
    }

    private static class TestView implements WeatherView {
        private boolean displayedWeather = false;
        private boolean displayedError = false;

        @Override
        public void showCurrentWeather(FullWeatherModel fullWeatherModel, String placeName) {
            displayedWeather = true;
        }

        @Override
        public void showErrorFragment() {
            displayedError = true;
        }

        public boolean isWeatherDisplayed() {
            return displayedWeather;
        }

        public boolean isErrorDisplayed() {
            return displayedError;
        }
    }


}
