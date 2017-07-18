package com.example.toor.yamblzweather.domain.interactors;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.di.modules.WeatherModule;

import javax.inject.Inject;

import timber.log.Timber;

public class InfoInteractor extends BaseInteracor {

    @Inject
    Context context;

    private static final String DEFAULT_VERSION = "0.0";

    @Override
    protected void inject() {
        App.getInstance().getAppComponent().plus(new WeatherModule()).inject(this);
    }

    public String getAppVersion() {
        Timber.v("ver");
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return DEFAULT_VERSION;
        }
    }
}
