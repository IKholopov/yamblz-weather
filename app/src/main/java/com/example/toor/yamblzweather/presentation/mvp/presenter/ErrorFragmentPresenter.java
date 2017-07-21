package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.content.Context;

import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.ConnectionErrorView;

import javax.inject.Inject;

public class ErrorFragmentPresenter extends BaseFragmentPresenter<ConnectionErrorView> {

    @Inject
    Context context;

    @Inject
    public ErrorFragmentPresenter() {

    }

    public void retryConnection() {
        if (NetworkConectionChecker.isNetworkAvailable(context)) {
            getView().showWeatherFragment();
        }
    }
}
