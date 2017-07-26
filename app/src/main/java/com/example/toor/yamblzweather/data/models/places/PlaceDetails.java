package com.example.toor.yamblzweather.data.models.places;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 7/26/17.
 */

public class PlaceDetails {
    @SerializedName("result")
    @Expose
    private PlaceDetailsResult result;

    public Coord getCoords() {
        Location location = result.getLocation();
        return new Coord(location.getLat(), location.getLon());
    }

    public String getName() {
        return result.getName();
    }
}

