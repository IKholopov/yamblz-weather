package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.mvp.view.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;

public class InfoFragment extends BaseFragment {

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.title_info);
    }

    @Override
    protected void setDrawableEnabled() {
        ((DrawerLocker)getActivity()).setDrawerEnable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}
