package com.example.toor.yamblzweather.presentation.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.example.toor.yamblzweather.R;

public class SettingsFragment extends PreferenceFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        SwitchPreference pref = (SwitchPreference) findPreference(getString(R.string.settings_temperature_key));
        //pref.setChecked(true); // You can check it already if needed to true or false or a value you have stored persistently
        pref.setWidgetLayoutResource(R.layout.custom_switch); // THIS IS THE KEY OF ALL THIS. HERE YOU SET A CUSTOM LAYOUT FOR THE WIDGET
        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // Here you can enable/disable whatever you need to
                return true;
            }
        });
    }
}
