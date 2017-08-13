package com.example.toor.yamblzweather.db;

import com.example.toor.yamblzweather.BuildConfig;
import com.example.toor.yamblzweather.common.SyncEntity;
import com.example.toor.yamblzweather.data.database.CupboardDB;
import com.example.toor.yamblzweather.data.database.PlaceDBModel;
import com.example.toor.yamblzweather.data.database.WeatherDBModel;
import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.models.weather.common.Temp;
import com.example.toor.yamblzweather.data.models.weather.common.Weather;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyForecastElement;
import com.example.toor.yamblzweather.data.models.weather.daily.DailyWeather;

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

import io.reactivex.functions.Action;
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

    private DailyWeather testModel;
    private PlaceDetails placeDetails;
    private CupboardDB cupboardDb;

    @Before
    public void prepare() {
        testModel = new DailyWeather();
        List<DailyForecastElement> elements = new ArrayList<>();
        DailyForecastElement element = new DailyForecastElement();
        element.setDt(1501772400);
        Weather weatherInfo = new Weather();
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weatherInfo);
        element.setWeather(weatherList);
        element.setHumidity(0);
        element.setPressure(0);
        Temp temp = new Temp();
        temp.setMax(0);
        temp.setMin(0);
        element.setTemp(temp);
        element.setDeg(0);
        element.setSpeed(0);
        DailyForecastElement element2 = new DailyForecastElement();
        element2.setDt(1601772400);
        element2.setWeather(weatherList);
        element2.setTemp(temp);
        element2.setPressure(0);
        element2.setHumidity(0);
        elements.add(element);
        elements.add(element2);
        testModel.setList(elements);

        placeDetails = PlaceDetails.newInstance(9000L, new Coord(2, 3), "TestName", "TESTID");

        cupboardDb = new CupboardDB(RuntimeEnvironment.application.getApplicationContext());
    }

    @Test
    public void testSaveWeatherToDatabase() throws InterruptedException {
        Long placeId = 0L;
        SyncEntity sync = new SyncEntity();
        RxDatabase db = cupboardDb.getDatabase();
        db.delete(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).subscribe(deleted -> {
            db.query(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).count()
                    .subscribe(before -> {
                        try {
                            assertThat(before, equalTo(0L));
                            cupboardDb.addOrUpdateWeather(testModel, placeId, () ->
                                    db.query(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).count()
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
        Long placeId = 1L;
        SyncEntity sync = new SyncEntity();
        RxDatabase db = cupboardDb.getDatabase();
        db.delete(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).subscribe(deleted -> {
            db.query(WeatherDBModel.class, "placeId = ?", String.valueOf(placeId)).count()
                    .subscribe(before -> {
                        try {
                            assertThat(before, equalTo(0L));
                            testModel.getList().get(0).setDt(2000000000);
                            cupboardDb.addOrUpdateWeather(testModel, placeId, () -> {
                                testModel.getList().get(0).setDt(300000000);
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
        Long placeId = 0L;
        SyncEntity sync = new SyncEntity();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_YEAR, 42);
        long firstDate = calendar.getTimeInMillis() / 1000;
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        long secondDate = calendar.getTimeInMillis() / 1000;
        testModel.getList().get(0).setDt((int)firstDate);
        testModel.getList().get(1).setDt((int)secondDate);
        cupboardDb.addOrUpdateWeather(testModel, placeId, () -> {
            cupboardDb.clearBeforeDate(calendar).subscribe(cleared ->
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
        placeDetails.setApiId("original");
        cupboardDb.addPlace(placeDetails, () ->
                cupboardDb.addPlace(placeDetails, () -> {
                    placeDetails.setApiId("diffetent");
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
                    cupboardDb.getWeather(placeDetails, 0).count()
                            .subscribe(count ->
                                sync.runAssertion(() -> assertThat(count, equalTo(2L))))));
        assertThat(sync.waitFor(), equalTo(true));
    }

    @Test
    public void testDeleteWeatherForPlace() throws InterruptedException {
        SyncEntity sync = new SyncEntity();
        Action a = () ->
                cupboardDb.addOrUpdateWeather(testModel, placeDetails.getId() + 1, () ->
                        cupboardDb.deleteWeatherForPlace(placeDetails.getId())
                                .subscribe(count ->
                                        sync.runAssertion(() -> assertThat(count, equalTo(2L)))));
        Long id = placeDetails.getId();
        cupboardDb.addOrUpdateWeather(testModel, id, a);
        assertThat(sync.waitFor(), equalTo(true));
    }

    @After
    public void cleanup() {
        cupboardDb.getDatabase().delete(WeatherDBModel.class);
        cupboardDb.getDatabase().delete(PlaceDBModel.class);
    }


}
