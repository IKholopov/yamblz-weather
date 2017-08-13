package com.example.toor.yamblzweather.data.models.weather.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 8/10/17.
 */

public class Temp {
    @SerializedName("day")
    @Expose
    private float day;
    @SerializedName("min")
    @Expose
    private float min;
    @SerializedName("max")
    @Expose
    private float max;
    @SerializedName("night")
    @Expose
    private float night;
    @SerializedName("eve")
    @Expose
    private float eve;
    @SerializedName("morn")
    @Expose
    private float morn;

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getEve() {
        return eve;
    }

    public void setEve(float eve) {
        this.eve = eve;
    }
}
