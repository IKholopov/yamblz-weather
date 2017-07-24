package com.example.toor.yamblzweather.data.models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 7/24/17.
 */

public class PlacesAutocomplete {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("predictions")
    @Expose
    private List<PlaceName> predictions = new ArrayList<PlaceName>();
}
