package com.example.toor.yamblzweather.data.models.places;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 7/24/17.
 */

public class PlacesAutocompleteModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("predictions")
    @Expose
    private List<PlaceName> predictions = new ArrayList<PlaceName>();

    @Nullable
    public PlaceName getPredictionAt(int position) {
        if(predictions == null || position >= predictions.size()) {
            return null;
        }
        return predictions.get(position);
    }

    public int getSize() {
        if(predictions == null) {
            return 0;
        }
        return predictions.size();
    }

    public void setPredictions(List<PlaceName> predictions) {
        this.predictions = predictions;
    }
}
