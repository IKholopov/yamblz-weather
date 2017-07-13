package com.example.toor.yamblzweather.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPreference {

    private static final String temperature = "temperature";

    private Context context;

    public SettingsPreference(Context context) {
        this.context = context;
    }

    public void saveTemperature(TemperatureType type) {
        SharedPreferences sharedPreferences = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(temperature, type.ordinal());
        editor.apply();
    }

    public TemperatureType getTemperature() {
        SharedPreferences sharedPreferences = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
        int temp = sharedPreferences.getInt(temperature, 0);
        return TemperatureType.values()[temp];
    }
}
