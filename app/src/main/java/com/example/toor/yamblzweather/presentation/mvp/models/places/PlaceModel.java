package com.example.toor.yamblzweather.presentation.mvp.models.places;

/**
 * Created by igor on 7/27/17.
 */

public class PlaceModel {
    private final String name;
    private final String placeId;
    private final double lat;
    private final double lon;

    public static class Builder {
        private String name;
        private String id;
        private double lat;
        private double lon;

        public Builder() {
            lat = 0;
            lon = 0;
        }

        public Builder(PlaceModel model) {
            name = model.name;
            lat = model.lat;
            lon = model.lon;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder placeId(String id) {
            this.id = id;
            return this;
        }

        public Builder coords(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
            return this;
        }

        public PlaceModel build() {
            return new PlaceModel(this);
        }
    }

    private PlaceModel(Builder builder) {
        this.name = builder.name;
        this.placeId = builder.id;
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
