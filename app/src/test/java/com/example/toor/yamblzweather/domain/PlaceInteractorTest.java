package com.example.toor.yamblzweather.domain;

import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlaceName;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepository;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepository;
import com.example.toor.yamblzweather.domain.interactors.PlacesInteractor;
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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by igor on 8/13/17.
 */

@RunWith(PowerMockRunner.class)
public class PlaceInteractorTest {
    private static final String AUTOCOMPLETE_INPUT = "autocomplete input";
    private static final String TEST_PLACE_ID = "test_id";
    private static final String PLACE_ONE = "place one";
    private static final String PLACE_TWO = "place two";


    private PlacesInteractor places;
    private PlacesAutocompleteModel placesAutocompleteModel;
    private PlaceDetails placeDetails;

    @Mock WeatherRepository weatherRepository;
    @Mock PlacesRepository placesRepository;

    @Before
    public void prepare() {
        placesAutocompleteModel = new PlacesAutocompleteModel();
        PlaceName first = new PlaceName();
        first.setName(PLACE_ONE);
        PlaceName second = new PlaceName();
        second.setName(PLACE_TWO);
        List<PlaceName> predictions = new ArrayList<>();
        predictions.add(first);
        predictions.add(second);
        placesAutocompleteModel.setPredictions(predictions);
        when(placesRepository.loadPlacesAutocomplete(eq(AUTOCOMPLETE_INPUT)))
                .thenReturn(Single.fromCallable(() -> placesAutocompleteModel));
        placeDetails = PlaceDetails.newInstance(0L, new Coord(0, 0), PLACE_TWO, TEST_PLACE_ID);
        when(placesRepository.loadPlaceDetails(eq(TEST_PLACE_ID)))
                .thenReturn(Single.fromCallable(() -> placeDetails));
        places = new PlacesInteractor(weatherRepository, placesRepository);
    }

    @Test
    public void testGetAutocomplete() {
        TestObserver<ArrayList<PlaceModel>> testObserver = new TestObserver<>();
        places.getAutocomplete(AUTOCOMPLETE_INPUT).subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValue(array ->
                array.size() == 2 && array.get(0).getName().equals(PLACE_ONE) &&
                array.get(1).getName().equals(PLACE_TWO));
    }

    @Test
    public void testGetPlaceDetails() {
        TestObserver<PlaceModel> testObserver = new TestObserver<>();
        places.getPlaceDetails(TEST_PLACE_ID).subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValue(model->
                model.getPlaceId().equals(TEST_PLACE_ID));
    }
}
