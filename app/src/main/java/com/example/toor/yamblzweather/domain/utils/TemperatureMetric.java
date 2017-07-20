package com.example.toor.yamblzweather.domain.utils;

public enum TemperatureMetric {
    CELSIUS("metric"),
    FAHRENHEIT("imperial");

    String unit;

    TemperatureMetric(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
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
