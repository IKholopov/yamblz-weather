package com.example.toor.yamblzweather.domain.utils;

public enum OWSupportedUnits {
    METRIC("metric"),
    FAHRENHEIT("imperial");

    String unit;

    OWSupportedUnits(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
