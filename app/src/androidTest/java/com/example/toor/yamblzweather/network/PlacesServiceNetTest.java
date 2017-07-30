package com.example.toor.yamblzweather.network;

import android.support.test.runner.AndroidJUnit4;

import com.example.toor.yamblzweather.SyncEntity;
import com.example.toor.yamblzweather.data.network.PlacesService;
import com.example.toor.yamblzweather.data.network.api.ApiKeys;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by igor on 7/29/17.
 */

@RunWith(AndroidJUnit4.class)
public class PlacesServiceNetTest {

    private PlacesService preparePlaceService() {
        return new PlacesService(ApiKeys.GOOGLE_PLACES_API_KEY);
    }

    @Test
    public void testPlaceAutocompleteServiceResponse() throws Exception {
        PlacesService service = preparePlaceService();
        String input = "mos";
        SyncEntity syncEntity = new SyncEntity();
        service.getAutocompleteForInput(input).subscribe(model -> {

                assertThat(
                        model.getPredictionAt(0).getName(), equalTo("Moscow, Russia"));
                syncEntity.notifyOne();
            }
        );
        syncEntity.waitFor();
    }

    @Test
    public void testPlaceDetailsServiceResponse() throws InterruptedException {
        PlacesService service = preparePlaceService();
        String placeId = "ChIJybDUc_xKtUYRTM9XV8zWRD0";
        SyncEntity syncEntity = new SyncEntity();
        service.getPlaceDetails(placeId).subscribe(details -> {
                assertThat(details.getName(),
                    equalTo("Moscow"));
                syncEntity.notifyOne();

        });
        syncEntity.waitFor();
    }
}
