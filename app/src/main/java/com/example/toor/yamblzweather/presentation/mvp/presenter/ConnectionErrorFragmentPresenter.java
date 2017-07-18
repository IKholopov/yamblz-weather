package com.example.toor.yamblzweather.presentation.mvp.presenter;

import com.example.toor.yamblzweather.domain.utils.NetworkConectionChecker;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;
import com.example.toor.yamblzweather.presentation.mvp.presenter.common.BaseFragmentPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.ConnectionErrorView;

import javax.inject.Inject;

public class ConnectionErrorFragmentPresenter extends BaseFragmentPresenter<ConnectionErrorView> {

    @Inject
    NetworkConectionChecker conectionChecker;

    @Inject
    public ConnectionErrorFragmentPresenter() {
    }

    @Override
    public void inject() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    public void retryConnection() {
        if (conectionChecker.isNetworkAvailable()) {
            getView().showWeatherFragment();
        }
    }
}
