package com.example.toor.yamblzweather.data.models.places;

import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.data.models.weather.common.Coord;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 7/26/17.
 */

public class PlaceDetails {
    private Long id;

    @SerializedName("result")
    @Expose
    private PlaceDetailsResult result = new PlaceDetailsResult();

    public static PlaceDetails newInstance(@Nullable Long id, Coord coords, String name) {
        PlaceDetails place = new PlaceDetails();
        place.id = id;
        place.setCoords(coords);
        place.setName(name);
        return place;
    }

    public Coord getCoords() {
        Location location = result.getLocation();
        return new Coord(location.getLat(), location.getLon());
    }

    public String getName() {
        return result.getName();
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        result.setName(name);
    }

    public void setCoords(Coord coords) {
        Location location = new Location();
        location.setLat(coords.getLat());
        location.setLon(coords.getLon());
        result.setLocation(location);
    }
}

