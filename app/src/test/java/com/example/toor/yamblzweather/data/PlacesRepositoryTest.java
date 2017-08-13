package com.example.toor.yamblzweather.data;

import com.example.toor.yamblzweather.common.AnswerWithSelf;
import com.example.toor.yamblzweather.common.AppMock;
import com.example.toor.yamblzweather.data.database.CupboardDB;
import com.example.toor.yamblzweather.data.database.DataBase;
import com.example.toor.yamblzweather.data.models.places.PlaceDetails;
import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.data.network.PlacesService;
import com.example.toor.yamblzweather.data.network.api.ApiKeys;
import com.example.toor.yamblzweather.data.network.api.GooglePlacesAPI;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepository;
import com.example.toor.yamblzweather.data.repositories.places.PlacesRepositoryImpl;
import com.example.toor.yamblzweather.data.repositories.weather.WeatherRepositoryImpl;
import com.example.toor.yamblzweather.presentation.di.App;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by igor on 8/13/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Retrofit.Builder.class, Retrofit.class, PlacesService.class,
        App.class, PlacesRepositoryImpl.class})
public class PlacesRepositoryTest {
    private static final String TEST_CITY_NAME = "TestName";
    private static final int TEST_AUTOCOMPLETE_SIZE = 1;

    private PlacesRepository repository;
    private AppMock mockedApp;

    @Mock CupboardDB dataBase;
    @Mock GooglePlacesAPI api;
    @Mock PlaceDetails placeDetails;
    @Mock PlacesAutocompleteModel placesAutocompleteModel;

    @Before
    public void prepare() throws Exception {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());

        mockedApp = AppMock.newInstance();

        PowerMockito.mockStatic(Retrofit.Builder.class);
        PowerMockito.mockStatic(Retrofit.class);
        Retrofit retrofit = PowerMockito.mock(Retrofit.class);
        when(retrofit.create(any())).thenReturn(api);
        Retrofit.Builder builder = mock(Retrofit.Builder.class, new AnswerWithSelf(Retrofit.Builder.class));
        whenNew(Retrofit.Builder.class).withNoArguments().thenReturn(builder);
        doReturn(retrofit).when(builder).build();

        when(placeDetails.getName()).thenReturn(TEST_CITY_NAME);
        when(placesAutocompleteModel.getSize()).thenReturn(TEST_AUTOCOMPLETE_SIZE);
        when(api.getPlaceDetails(anyString(), anyString(), anyString()))
                .thenReturn(Single.fromCallable(() -> placeDetails));
        when(api.getPlacesAutocomplete(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Single.fromCallable(() -> placesAutocompleteModel));
        repository = new PlacesRepositoryImpl(dataBase, new PlacesService(ApiKeys.GOOGLE_PLACES_API_KEY));
    }

    @Test
    public void loadPlacesAutocompleteTest() throws InterruptedException {
        TestObserver<PlacesAutocompleteModel> testObserver = new TestObserver<>();
        repository.loadPlacesAutocomplete("some stirng").subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(model -> model.getSize() == TEST_AUTOCOMPLETE_SIZE);

    }

    @Test
    public void loadPlacesDetails() {
        TestObserver<PlaceDetails> testObserver = new TestObserver<>();
        repository.loadPlaceDetails(TEST_CITY_NAME).subscribe(testObserver);
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(place -> place.getName().equals(TEST_CITY_NAME));
    }
}
