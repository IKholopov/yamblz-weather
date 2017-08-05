package com.example.toor.yamblzweather.data.database;

import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;

/**
 * Model for cupboard database
 * Created by igor on 8/2/17.
 */

public class PlaceDBModel {
    public Long _id;
    public Float latitude;
    public Float longitude;
    public String name;
    public boolean currentLocation;

    public PlaceDBModel() {
        latitude = 0.0f;
        longitude = 0.0f;
        name = "NO_NAME";
        currentLocation = false;
    }

    public PlaceDBModel(float latitude, float longitude, String name, boolean currentLocation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.currentLocation = currentLocation;
    }
}
