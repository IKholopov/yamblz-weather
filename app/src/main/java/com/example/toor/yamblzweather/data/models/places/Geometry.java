package com.example.toor.yamblzweather.data.models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 7/26/17.
 */

public class Geometry {
    @SerializedName("location")
    @Expose
    Location location;

    @SerializedName("northeast")
    @Expose
    Location northeast;

    @SerializedName("southwest")
    @Expose
    Location southwest;

    public void setLocation(Location location) {
        this.location = location;
    }
}
