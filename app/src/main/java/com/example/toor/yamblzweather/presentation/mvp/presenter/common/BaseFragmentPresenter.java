package com.example.toor.yamblzweather.presentation.mvp.presenter.common;

import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public abstract class BaseFragmentPresenter<V extends BaseView>{

    private V view;

    public void onAttach(V view) {
        this.view = view;
    }

    public void onDetach() {
        this.view = null;
    }

    public void onDestroy() {

    }

    protected @Nullable
    V getView() {
        return view;
    }

}
