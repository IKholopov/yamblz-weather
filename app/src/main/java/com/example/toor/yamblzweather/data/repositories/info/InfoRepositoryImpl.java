package com.example.toor.yamblzweather.data.repositories.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.example.toor.yamblzweather.presentation.di.App;

import javax.inject.Inject;

import io.reactivex.Single;

public class InfoRepositoryImpl implements InfoRepository {

    @Inject
    Context context;

    private static final String DEFAULT_VERSION = "0.0";

    public InfoRepositoryImpl() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public @NonNull Single<String> getAppVersion() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return Single.fromCallable(() -> info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Single.fromCallable(() -> DEFAULT_VERSION);
        }
    }
}
