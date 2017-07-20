package com.example.toor.yamblzweather.presentation.mvp.presenter;

import android.content.Context;

import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.ActivityModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.ConnectionErrorView;

import javax.inject.Inject;

public class ConnectionErrorFragmentPresenter extends BaseFragmentPresenter<ConnectionErrorView> {

    @Inject
    Context context;

    @Inject
    public ConnectionErrorFragmentPresenter() {

    }

    @Override
    public void inject() {
    }

    public void retryConnection() {
        if (NetworkConectionChecker.isNetworkAvailable(context)) {
            getView().showWeatherFragment();
        }
    }
}
