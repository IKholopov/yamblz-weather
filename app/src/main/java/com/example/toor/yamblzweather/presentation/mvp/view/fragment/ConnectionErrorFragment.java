package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toor.yamblzweather.R;

public class ConnectionErrorFragment extends Fragment {



    public static ConnectionErrorFragment newInstance() {
        return new ConnectionErrorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connection_error, container, false);
    }
}
