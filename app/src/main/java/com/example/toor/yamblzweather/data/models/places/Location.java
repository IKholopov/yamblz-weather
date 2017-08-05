package com.example.toor.yamblzweather.data.models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 7/26/17.
 */

public class Location {
    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lng")
    @Expose
    private double lng;

    public double getLon() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLon(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
