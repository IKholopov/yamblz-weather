package com.example.toor.yamblzweather.data;

import com.example.toor.yamblzweather.common.AnswerWithSelf;
import com.example.toor.yamblzweather.common.AppMock;
import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.current_day.CurrentWeather;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.data.network.api.ApiKeys;
import com.example.toor.yamblzweather.data.network.api.OpenWeatherAPI;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepositoryImpl;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.components.AppComponent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
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

    private AppMock mockedApp;

    @Mock OpenWeatherAPI api;
    @Mock DataBase dataBase;
    @Mock CurrentWeather weather;

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

        when(weather.getName()).thenReturn(TEST_CITY);
        when(api.getCurrentWeatherForCoords(eq(TEST_COORDS.getLat()), eq(TEST_COORDS.getLon()),
                anyString(), anyString())).thenReturn(Single.fromCallable(() -> weather));

        CurrentWeather testWeather = new CurrentWeather();
        testWeather.setCoord(TEST_COORDS);
        testWeather.setName(TEST_CITY);
        when(dataBase.getCurrentWeather(any())).thenReturn(Single.fromCallable(() -> testWeather));
    }

    @Test
    public void loadCurrentWeatherFromNWTest() {
        WeatherRepository repository = prepareWeatherRepository();
        TestObserver<CurrentWeather> testObserver = new TestObserver<>();
        repository.loadCurrentWeatherFromNW(TEST_COORDS).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(weather -> weather.getName().equals(TEST_CITY));
    }

    @Test
    public void loadCurrentWeatherFromDBTest() {
        WeatherRepository repository = prepareWeatherRepository();
        TestObserver<CurrentWeather> testObserver = new TestObserver<>();
        repository.getCurrentWeatherFromDB(TEST_COORDS).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(weather -> weather.getName().equals(TEST_CITY));
    }
}
