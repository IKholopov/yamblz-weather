package com.example.toor.yamblzweather.data.models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceDetailsResult {
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("name")
    @Expose
    private String name;

    Location getLocation() {
        return geometry.location;
    }

    public String getName() {
        return name;
    }
}
