package com.example.toor.yamblzweather.presentation.mvp.view.fragment.common;

import android.content.Context;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        setTitle();
    }

    protected abstract void setTitle();
}
