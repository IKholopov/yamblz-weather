package com.example.toor.yamblzweather.presentation.common;

public interface BaseFragmentPresenter<View>{

    void setModel();

    void bindView(View view);
}
