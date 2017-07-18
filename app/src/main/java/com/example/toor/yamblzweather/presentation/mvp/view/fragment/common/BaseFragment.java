package com.example.toor.yamblzweather.presentation.mvp.view.fragment.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        setDrawableEnabled();
        setTitle();
    }

    protected abstract void setTitle();
    protected abstract void setDrawableEnabled();
}