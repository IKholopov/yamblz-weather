package com.example.toor.yamblzweather.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.view.fragment.common.BaseFragment;

public class SettingsFragment extends BaseFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_settings);
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}
