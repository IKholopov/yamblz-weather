package com.example.toor.yamblzweather.domain;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlaceName;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepository;
import com.example.toor.yamblzweather.data.repositories.settings.SettingsRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
import com.example.toor.yamblzweather.domain.interactors.WeatherInteractor;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 8/13/17.
 */

@RunWith(PowerMockRunner.class)
public class WeatherInteractorTest {
    private static final String PLACE_ID = "place_id";
    private static final String PLACE_NAME = "place_name";
    private static final Long LONG_ID = 0L;
    private static final int CLOUDS = 34;

    private WeatherInteractor interactor;
    private PlaceModel placeModel;
    private DailyWeather weather;

    @Mock WeatherRepository weatherRepository;

    @Before
    public void prepare() {
        weather = new DailyWeather();
        weather.setCity(new City());
        List<DailyForecastElement> elements = new ArrayList<>();
        DailyForecastElement element = new DailyForecastElement();
        element.setClouds(CLOUDS);
        elements.add(element);
        weather.setCnt(1);
        weather.setList(elements);
        when(weatherRepository.getExtendedWeatherFromDB(any(), anyLong()))
                .thenReturn(Single.fromCallable(() -> weather));
        when(weatherRepository.loadExtendedWeatherFromNW(any()))
                .thenReturn(Single.fromCallable(() -> weather));
        placeModel = new PlaceModel.Builder().placeId(PLACE_ID).coords(0, 0).localId(LONG_ID)
                .name(PLACE_NAME).build();
        interactor = new WeatherInteractor(weatherRepository);
    }

    @Test
    public void testGetWeather() {
        TestObserver<DailyWeather> testObserver = new TestObserver<>();
        interactor.getWeatherFromDB(placeModel).subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValue(weather ->
              weather.getList().size() == 1 && weather.getList().get(0).getClouds() == CLOUDS);
    }

    @Test
    public void testUpdate() {
        TestObserver<DailyWeather> testObserver = new TestObserver<>();
        interactor.updateWeather(placeModel).subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValue(model-> {
            verify(weatherRepository, times(1)).saveWeather(any(),any());
            verify(weatherRepository, times(1)).clearOldRecords(any());
            return true;
        });
    }
}
