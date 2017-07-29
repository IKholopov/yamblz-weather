package com.example.toor.yamblzweather;

import android.support.test.runner.AndroidJUnit4;

import com.example.toor.yamblzweather.data.network.PlacesService;
import com.example.toor.yamblzweather.data.network.api.ApiKeys;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by igor on 7/29/17.
 */

@RunWith(AndroidJUnit4.class)
public class NetworkTests {

    PlacesService preparePlaceService() {
        return new PlacesService(ApiKeys.GOOGLE_PLACES_API_KEY);
    }

    @Test
    public void testPlaceAutocompleteServiceResponse() throws Exception {
        PlacesService service = preparePlaceService();
        String input = "mos";
        service.getAutocompleteForInput(input).subscribe(model -> {
                assertThat(
                        model.getPredictionAt(0).getName(), equals("Moscow, Russia"));
            }
        );
    }

    @Test
    public void testPlaceDetailsServiceResponse() {
        PlacesService service = preparePlaceService();
        String placeId = "ChIJybDUc_xKtUYRTM9XV8zWRD0";
        service.getPlaceDetails(placeId).subscribe(details -> assertThat(details.getName(), equals("Moscow")));
    }
}
