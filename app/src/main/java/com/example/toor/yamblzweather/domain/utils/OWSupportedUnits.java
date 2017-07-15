package com.example.toor.yamblzweather.domain.utils;

public enum OWSupportedUnits {
    CELSIUS("metric"),
    FAHRENHEIT("imperial");

    String unit;

    OWSupportedUnits(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public static OWSupportedUnits fromString(String text) {
        for (OWSupportedUnits b : OWSupportedUnits.values()) {
            if (b.unit.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
