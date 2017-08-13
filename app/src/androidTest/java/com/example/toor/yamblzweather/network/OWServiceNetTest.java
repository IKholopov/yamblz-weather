package com.example.toor.yamblzweather.network;

import android.support.test.runner.AndroidJUnit4;

import com.example.toor.yamblzweather.SyncEntity;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.network.OWService;
import com.example.toor.yamblzweather.data.network.api.ApiKeys;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

/**
 * Created by igor on 7/29/17.
 */

@RunWith(AndroidJUnit4.class)
public class OWServiceNetTest {
    private OWService prepareOWService() {
        return new OWService(ApiKeys.OPEN_WEATHER_MAP_API_KEY);
    }

    @Test
    public void testOWGetExtendedWeatherFromCoordsResponce() throws InterruptedException {
        OWService service = prepareOWService();
        Coord testCoords = new Coord(55.895159, 37.557798);
        SyncEntity syncEntity = new SyncEntity();
        service.getExtendedWeather(testCoords)
                .subscribe(weather -> {
                    assertThat((double)weather.getList().get(0).getTemp().getDay(),
                            closeTo(300.0, 80.0));
                    syncEntity.notifyOne();
                });
        syncEntity.waitFor();
    }
}
