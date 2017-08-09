package com.example.toor.yamblzweather.presentation.mvp.view;

import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

import java.util.List;

/**
 * Created by igor on 8/7/17.
 */

public interface PlacesListView extends BaseView {
    void updatePlacesSuggestionList(List<PlaceModel> suggestions);
}
