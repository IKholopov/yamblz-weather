package com.example.toor.yamblzweather.presentation.mvp.view.fragment.common;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.toor.yamblzweather.presentation.mvp.view.drawer.DrawerLocker;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        setTitle();
        setDrawableEnabled();
    }

    protected abstract void setTitle();
    protected abstract void setDrawableEnabled();
}