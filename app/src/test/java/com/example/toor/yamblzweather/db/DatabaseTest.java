package com.example.toor.yamblzweather.db;

import com.example.toor.yamblzweather.BuildConfig;
import com.example.toor.yamblzweather.common.SyncEntity;
import com.example.toor.yamblzweather.data.database.CupboardDB;
import com.example.toor.yamblzweather.data.database.PlaceDBModel;
import com.example.toor.yamblzweather.data.database.WeatherDBModel;
import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.common.Main;
import com.example.toor.yamblzweather.data.models.weather.common.Weather;
import com.example.toor.yamblzweather.data.models.weather.common.Wind;
import com.example.toor.yamblzweather.data.models.weather.five_day.ExtendedWeather;
import com.example.toor.yamblzweather.data.models.weather.five_day.WeatherForecastElement;
import com.example.toor.yamblzweather.domain.utils.TemperatureMetric;
import com.example.toor.yamblzweather.presentation.mvp.models.weather.FullWeatherModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nl.nl2312.rxcupboard2.RxDatabase;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by igor on 8/5/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = {LOLLIPOP})
public class DatabaseTest {

    private FullWeatherModel testModel;
    private PlaceDetails placeDetails;
    private CupboardDB cupboardDb;

    @Before
    public void prepare() {
        ExtendedWeather weather = new ExtendedWeather();
        List<WeatherForecastElement> elements = new ArrayList<>();
        WeatherForecastElement element = new WeatherForecastElement();
        element.setDt(1501772400);
        Weather weatherInfo = new Weather();
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weatherInfo);
        element.setWeather(weatherList);
        Main main = new Main();
        main.setHumidity(0);
        main.setPressure(0.);
        main.setTempMax(0.);
        main.setTempMin(0.);
        Wind wind = new Wind();
        wind.setDeg(0.);
        wind.setSpeed(0.);
        element.setWind(wind);
        element.setMain(main);
        WeatherForecastElement element2 = new WeatherForecastElement();
        element2.setDt(1601772400);
        element2.setWeather(weatherList);
        element2.setWind(wind);
        element2.setMain(main);
        elements.add(element);
        elements.add(element2);
        weather.setList(elements);
        testModel = new FullWeatherModel(null, weather, TemperatureMetric.CELSIUS);

        placeDetails = new PlaceDetails();
        placeDetails.setName("TestName");
        placeDetails.setCoords(new Coord(2, 3));

        cupboardDb = new CupboardDB(RuntimeEnvironment.application.getApplicationContext());
    }

    @Test
    public void testSaveWeatherToDatabase() throws InterruptedException {
        int placeId = 0;
        SyncEntity sync = new SyncEntity();
        RxDatabase db = cupboardDb.getDatabase();
        db.delete(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).subscribe(deleted -> {
            db.query(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).count()
                    .subscribe(before -> {
                        try {
                            assertThat(before, equalTo(0L));
                            cupboardDb.addOrUpdateWeather(testModel, placeId, () -> db.query(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).count()
                                    .subscribe(count ->
                                            sync.runAssertion(() -> assertThat(count, equalTo(2L)))
                                    ));
                        } catch (AssertionError e) {
                            sync.onFail();
                            sync.notifyOne();
                            throw  e;
                        }
                    });
        });
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testUpdateWeatherToDatabase() throws InterruptedException {
        int placeId = 1;
        SyncEntity sync = new SyncEntity();
        RxDatabase db = cupboardDb.getDatabase();
        db.delete(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).subscribe(deleted -> {
            db.query(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).count()
                    .subscribe(before -> {
                        try {
                            assertThat(before, equalTo(0L));
                            testModel.getWeatherForecast().getList().get(0).setDt(2000000000);
                            cupboardDb.addOrUpdateWeather(testModel, placeId, () -> {
                                testModel.getWeatherForecast().getList().get(0).setDt(300000000);
                                cupboardDb.addOrUpdateWeather(testModel, placeId, () -> {
                                    db.query(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).count()
                                            .subscribe(count ->
                                                    sync.runAssertion(() -> assertThat(count, equalTo(3L))));
                                });
                            });
                        } catch (AssertionError e){
                            sync.onFail();
                            sync.notifyOne();
                        }
                    });
        });
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testCleanUp() throws InterruptedException {
        int placeId = 0;
        SyncEntity sync = new SyncEntity();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_YEAR, 42);
        long firstDate = calendar.getTimeInMillis() / 1000;
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        long secondDate = calendar.getTimeInMillis() / 1000;
        testModel.getWeatherForecast().getList().get(0).setDt((int)firstDate);
        testModel.getWeatherForecast().getList().get(1).setDt((int)secondDate);
        cupboardDb.addOrUpdateWeather(testModel, placeId, () -> {
            cupboardDb.clearAfterDate(calendar).subscribe(cleared ->
                    sync.runAssertion(() -> assertThat(cleared, equalTo(1L))));
        });
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testAddPlace() throws InterruptedException {
        SyncEntity sync = new SyncEntity();
        cupboardDb.addPlace(placeDetails, () -> {
            cupboardDb.getDatabase().query(PlaceDBModel.class, "latitude = ? AND longitude = ?",
                    String.valueOf(placeDetails.getCoords().getLat()),
                    String.valueOf(placeDetails.getCoords().getLon()))
                    .subscribe(placeDBModel ->
                        sync.runAssertion(() ->
                                assertThat(placeDBModel.name, equalTo(placeDetails.getName()))));
        });
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testAddDuplicatePlace() throws InterruptedException {
        SyncEntity sync = new SyncEntity();
        cupboardDb.addPlace(placeDetails, () ->
                cupboardDb.addPlace(placeDetails, () -> {
                    placeDetails.setCoords(new Coord(-100, -100));
                    cupboardDb.addPlace(placeDetails, () ->
                        cupboardDb.getDatabase().count(PlaceDBModel.class)
                         .subscribe(count ->
                                 sync.runAssertion(() -> assertThat(count, equalTo(2L)))));
                }));
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testRemovePlace() throws InterruptedException {
        SyncEntity sync = new SyncEntity();
        cupboardDb.addPlace(placeDetails, () -> {
            cupboardDb.getDatabase().query(PlaceDBModel.class, "latitude = ? AND longitude = ?",
                    String.valueOf(placeDetails.getCoords().getLat()),
                    String.valueOf(placeDetails.getCoords().getLon()))
                    .subscribe(placeDBModel -> cupboardDb.deletePlace(placeDBModel._id)
                            .subscribe(deleted ->
                                sync.runAssertion(() -> assertThat(deleted, equalTo(1L)))));
        });
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testGetPlaces() throws InterruptedException {
        SyncEntity sync = new SyncEntity();
        cupboardDb.addPlace(placeDetails, () ->
            cupboardDb.getPlaces().count().subscribe(count ->
                sync.runAssertion(() -> assertThat(count, equalTo(1L)))));
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testAddCurrentLocation() throws InterruptedException {
        SyncEntity sync = new SyncEntity();
        cupboardDb.addCurrentLocation(placeDetails, () ->
                cupboardDb.addCurrentLocation(placeDetails, () ->
                        cupboardDb.getDatabase().count(PlaceDBModel.class)
                                .subscribe(count ->
                                        sync.runAssertion(() -> assertThat(count, equalTo(1L))))));
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testGetWeather() throws InterruptedException {
        SyncEntity sync = new SyncEntity();
        cupboardDb.addOrUpdateWeather(testModel, placeDetails.getId(), () ->
                cupboardDb.addOrUpdateWeather(testModel, placeDetails.getId() + 1, () ->
                    cupboardDb.getWeather(placeDetails).count()
                            .subscribe(count ->
                                sync.runAssertion(() -> assertThat(count, equalTo(2L))))));
        assertThat(sync.waitFor(), equalTo(true));
    }

    @After
    public void cleanup() {
        cupboardDb.getDatabase().delete(WeatherDBModel.class);
        cupboardDb.getDatabase().delete(PlaceDBModel.class);
    }


}
