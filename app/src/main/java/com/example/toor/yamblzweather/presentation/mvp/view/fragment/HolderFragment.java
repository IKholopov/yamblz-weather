package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;

public class HolderFragment<T extends BaseFragmentPresenter> extends Fragment {

    public T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public
    @Nullable
    T getPresenter() {
        return this.presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.presenter != null)
            this.presenter.onDestroy();
    }
}