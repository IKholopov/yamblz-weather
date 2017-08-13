package com.example.toor.yamblzweather.data.models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 7/24/17.
 */

public class PlaceName {
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("place_id")
    @Expose
    private String placeId;

    public String getName() {
        return description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setName(String name) {
        this.description = name;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
