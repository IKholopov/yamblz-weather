package com.example.toor.yamblzweather.data;

import com.example.toor.yamblzweather.common.AnswerWithSelf;
import com.example.toor.yamblzweather.common.AppMock;
import com.example.toor.yamblzweather.data.database.CupboardDB;
import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.common.City;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.data.network.api.ApiKeys;
import com.example.toor.yamblzweather.data.network.api.OpenWeatherAPI;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepositoryImpl;
import com.example.toor.yamblzweather.presentation.di.App;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Weather Repository and OWService test
 * Created by igor on 7/30/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Retrofit.Builder.class, Retrofit.class, OWService.class,
        App.class, WeatherRepositoryImpl.class})
public class WeatherRepositoryTest {

    private static final String TEST_CITY = "TestWeatherCity";
    private static final Coord TEST_COORDS = new Coord(10, 10);
    private static final int TEST_DT = 10;

    private AppMock mockedApp;
    private PlaceDetails place;
    private City city;

    @Mock OpenWeatherAPI api;
    @Mock CupboardDB dataBase;
    @Mock DailyWeather weather;

    private WeatherRepository prepareWeatherRepository() {
        return new WeatherRepositoryImpl(dataBase, new OWService(ApiKeys.OPEN_WEATHER_MAP_API_KEY));
    }

    @Before
    public void prepare() throws Exception {
        mockedApp = AppMock.newInstance();

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());

        PowerMockito.mockStatic(Retrofit.Builder.class);
        PowerMockito.mockStatic(Retrofit.class);
        Retrofit retrofit = PowerMockito.mock(Retrofit.class);
        Mockito.when(retrofit.create(any())).thenReturn(api);
        Retrofit.Builder builder = mock(Retrofit.Builder.class, new AnswerWithSelf(Retrofit.Builder.class));
        whenNew(Retrofit.Builder.class).withNoArguments().thenReturn(builder);
        doReturn(retrofit).when(builder).build();

        city = new City();
        city.setName(TEST_CITY);
        city.setCoord(TEST_COORDS);

        when(weather.getCity()).thenReturn(city);
        when(api.getFiveDayExtendedWeather(eq(TEST_COORDS.getLat()), eq(TEST_COORDS.getLon()),
                anyString(), anyString(), anyInt())).thenReturn(Single.fromCallable(() -> weather));

        DailyForecastElement testWeather = new DailyForecastElement();

        place = PlaceDetails.newInstance(0L, TEST_COORDS, TEST_CITY, "");

        testWeather.setDt(TEST_DT);

        when(dataBase.getWeather(any(), anyLong()))
                .thenReturn(Flowable.fromCallable(() -> testWeather));
    }

    @Test
    public void loadCurrentWeatherFromNWTest() {
        WeatherRepository repository = prepareWeatherRepository();
        TestObserver<DailyWeather> testObserver = new TestObserver<>();
        repository.loadExtendedWeatherFromNW(place).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(weather -> weather.getCity().getName().equals(TEST_CITY));
    }

    @Test
    public void loadCurrentWeatherFromDBTest() {
        WeatherRepository repository = prepareWeatherRepository();
        TestObserver<DailyWeather> testObserver = new TestObserver<>();
        repository.getExtendedWeatherFromDB(place, 0).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(weather -> weather.getList().get(0).getDt().equals(TEST_DT));
    }
}
