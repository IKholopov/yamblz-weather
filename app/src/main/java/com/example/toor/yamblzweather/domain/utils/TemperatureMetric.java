package com.example.toor.yamblzweather.domain.utils;

import android.support.annotation.NonNull;

public enum TemperatureMetric {
    CELSIUS("metric"),
    FAHRENHEIT("imperial");

    private String unit;

    TemperatureMetric(String unit) {
        this.unit = unit;
    }

    public @NonNull String getUnit() {
        return unit;
    }

    public static TemperatureMetric fromString(String text) {
        for (TemperatureMetric b : TemperatureMetric.values()) {
            if (b.unit.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
