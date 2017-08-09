package com.example.toor.yamblzweather.presentation.mvp.models.places;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by igor on 7/27/17.
 */

public class PlaceModel implements Parcelable{
    private final String name;
    private final String placeId;
    private final Long localId;
    private final double lat;
    private final double lon;

    private PlaceModel(Parcel parcel) {
        this.localId = parcel.readLong();
        this.name = parcel.readString();
        this.placeId = parcel.readString();
        this.lat = parcel.readDouble();
        this.lon = parcel.readDouble();
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel in) {
            in.setDataPosition(0);
            return new PlaceModel(in);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(localId);
        parcel.writeString(name);
        parcel.writeString(placeId);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
    }

    public static class Builder {
        private String name;
        private String placeId;
        private Long localId;
        private double lat;
        private double lon;

        public Builder() {
            lat = 0;
            lon = 0;
        }

        public Builder(PlaceModel model) {
            name = model.name;
            placeId = model.placeId;
            localId = model.localId;
            lat = model.lat;
            lon = model.lon;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder placeId(String id) {
            this.placeId = id;
            return this;
        }

        public Builder localId(Long id) {
            this.localId = id;
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
        this.placeId = builder.placeId;
        this.localId = builder.localId;
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public Long getLocalId() {
        return localId;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
