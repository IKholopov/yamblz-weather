package com.example.toor.yamblzweather.presentation.mvp.presenter.common;

import android.support.annotation.Nullable;

import com.example.toor.yamblzweather.presentation.mvp.view.common.BaseView;

public abstract class BaseFragmentPresenter<T extends BaseView>{

    private T view;

    public void onAttach(T view) {
        this.view = view;
    }

    public void onDetach() {
        this.view = null;
    }

    public void onDestroy() {

    }

    protected @Nullable T getView() {
        return view;
    }

}
