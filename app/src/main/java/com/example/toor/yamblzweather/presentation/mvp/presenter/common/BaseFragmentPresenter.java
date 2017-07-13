package com.example.toor.yamblzweather.presentation.mvp.presenter.common;

public interface BaseFragmentPresenter<View>{

    void setModel();

    void bindView(View view);
}
