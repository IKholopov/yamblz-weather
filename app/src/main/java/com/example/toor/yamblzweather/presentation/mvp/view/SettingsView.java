package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.data.models.places.PlacesAutocompleteModel;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.models.settings.SettingsModel;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface SettingsView extends BaseView {

    void setSettings(SettingsModel settingsModel);
    void updateCitiesSuggestionList(ArrayList<PlaceModel> places);
    void displayError(String message);
}
