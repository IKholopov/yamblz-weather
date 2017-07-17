package com.example.toor.yamblzweather.domain.utils;

public enum OWSupportedMetric {
    CELSIUS("metric"),
    FAHRENHEIT("imperial");

    String unit;

    OWSupportedMetric(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public static OWSupportedMetric fromString(String text) {
        for (OWSupportedMetric b : OWSupportedMetric.values()) {
            if (b.unit.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
